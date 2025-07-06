import React, {useState, useEffect} from 'react';
import {useParams} from "react-router-dom";
import {useCart} from "../Context/CartContext.jsx";
import {getProductById} from "../../Services/ApiFunction.js";


const ProductDetailPage = () => {
    const {productId} = useParams();
    const {carts, dispatch} = useCart();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await getProductById(productId);
                const data = response.data || null;
                setProduct(data);
            } catch (error) {
                console.error('Error fetching product:', error);
            }
        }
        fetchProduct();
    }, [productId]);

    const addToCart = (product) => {
        if (!product) return;
        dispatch({type: 'ADD_ITEM', payload: product});
    };

    const incrementItem = (product) => {
        if (!product) return;
        dispatch({type: 'INCREMENT_ITEM', payload: product});
    };

    const decrementItem = (product) => {
        if (!product) return;
        const cartItem = carts.find(item => item.id === product.id);
        if (cartItem && cartItem.quantity > 1) {
            dispatch({type: 'DECREMENT_ITEM', payload: product});
        } else {
            dispatch({type: 'REMOVE_ITEM', payload: product});
        }
    }

    if (!product) {
        return <div className="text-center mt-5">Loading product details...</div>;
    }

    const cartItem = carts.find(item => item.id === product.id);

    return (
        <div className="product-detail max-w-[350px] my-40 mx-auto p-4 border border-[#ccc] rounded-[10px]
        flex flex-col items-center gap-4">
            <img src={product.imageUrl} alt={product.name} className="w-[350px] h-auto"/>
            <h1 className="text-center text-xl font-semibold">{product.name}</h1>
            <p>{product.description}</p>
            <span className="block text-[20px] text-[#f68b1e]">{product.price.toFixed(2)}</span>
            {cartItem ? (
                <div className="quantity-control flex items-center justify-center my-2.5">
                    <button className="bg-[#f68b1e] text-white text-xl px-2.5
                                py-1.5 rounded transition-colors duration-300 hover:bg-[#633d17] cursor-pointer"
                            onClick={() => decrementItem(product)}> - </button>
                    <span className="mx-2.5 text-xl text-[#333]">{cartItem.quantity}</span>
                    <button className="bg-[#f68b1e] text-white text-xl px-2.5
                                py-1.5 rounded transition-colors duration-300 hover:bg-[#633d17] cursor-pointer"
                            onClick={() => incrementItem(product)}> + </button>
                </div>
            ) : (
                <button className="bg-[#f68b1e] text-white font-medium text-base px-5 py-2.5 rounded
                            mt-2 mb-2 transition-colors duration-300 hover:bg-[#633d17] cursor-pointer"
                        onClick={() => addToCart(product)}>
                    Add to Cart
                </button>
            )}


        </div>
    );
};

export default ProductDetailPage;
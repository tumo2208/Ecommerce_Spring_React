import React, {useState, useEffect} from 'react';
import {Link} from "react-router-dom";
import {useCart} from "../Context/CartContext.jsx";

const ProductList = ({products}) => {
    const {carts, dispatch} = useCart();

    const addToCart = (product) => {
        dispatch({type: 'ADD_ITEM', payload: product});
    };

    const incrementItem = (product) => {
        dispatch({type: 'INCREMENT_ITEM', payload: product});
    };

    const decrementItem = (product) => {
        const cartItem = carts.find(item => item.id === product.id);
        if (cartItem && cartItem.quantity > 1) {
            dispatch({type: 'DECREMENT_ITEM', payload: product});
        } else {
            dispatch({type: 'REMOVE_ITEM', payload: product});
        }
    }

    return (
        <div className="product-list flex flex-wrap justify-center gap-5 p-5 mx-20">
            {products.map((product, index) => {
                const cartItem = carts.find(item => item.id === product.id);
                return (
                    <div className="product-item border border-[#e0e0e0] shadow-md overflow-hidden text-center
                    transition-transform duration-300 ease-in-out w-[250px]
                    hover:shadow-lg hover:-translate-x-1.5" key={index}>
                        <Link className="text-inherit no-underline" to={`/product/${product.id}`}>
                            <img src={product.imageUrl} alt={product.name}
                                 className="product-img w-full h-[200px] object-cover"/>
                            <h3 className="text-[#333] text-[1.2em] my-2.5 font-semibold">{product.name}</h3>
                            {/*<p className="text-[#777] text-sm mx-5 my-2.5 h-[60px] overflow-hidden">*/}
                            {/*    {product.description}*/}
                            {/*</p>*/}
                            <span className="text-[#333] text-[1.1em] my-2.5 block">{product.price.toFixed(2)}</span>
                        </Link>
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
                )
            })}
        </div>
    );
};

export default ProductList;
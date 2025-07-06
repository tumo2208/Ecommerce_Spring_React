import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useCart} from "../Context/CartContext.jsx";
import {isAuthenticated, placeOrder} from "../../Services/ApiFunction.js";


const CartPage = () => {
    const navigate = useNavigate();
    const {carts, dispatch} = useCart();
    const [message, setMessage] = useState('');

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

    const totalPrice = carts.reduce((total, item) =>
        total + item.price * item.quantity, 0).toFixed(2);

    const handleCheckout = async () => {
        if (!isAuthenticated()) {
            setMessage('Please login to proceed with checkout.');
            setTimeout(() => {
                navigate('/login');
            }, 3000);
        }

        const orderItems = carts.map(item => ({
            productId: item.id,
            quantity: item.quantity
        }));

        const orderRequest = {
            totalPrice: totalPrice,
            orderItems: orderItems
        }

        try {
            const response = await placeOrder(orderRequest);
            if (response.status === 200) {
                setMessage(response.message);
                dispatch({type: 'CLEAR_CART'});
                setTimeout(() => {
                    navigate('/');
                }, 3000);
            }
        } catch (error) {
            setMessage('Failed to place order. Please try again later.');
        }
    };

    return (
        <div className="cart-page max-w-[800px] mx-auto mb-40 p-5 text-[#444]">
            <h1 className="font-bold">Cart</h1>
            {message && <div className="response-message text-[#f68b1e] text-[25px] font-bold text-center">
                {message}
            </div>}
            { carts.length === 0 ? (
                <p>Your cart is empty</p>
            ) : (
                <div>
                    <ul className="list-none p-0">
                        {carts.map(product => (
                            <li key={product.id} className="border border-[#ddd] rounded mb-5 p-4
                            flex items-center justify-between">
                                <div className="flex items-center gap-4">
                                    <img src={product.imageUrl} alt={product.name}
                                         className="w-20 h-20 object-cover rounded mr-5"/>
                                    <h2 className="text-[#444]">{product.name}</h2>
                                </div>

                                <div className="flex flex-col items-center gap-2">
                                    <div className="quantity-control flex items-center gap-2.5">
                                        <button className="bg-[#f68b1e] text-white rounded px-2.5 py-1 text-xl
                                        cursor-pointer hover:bg-[#93500d] transition-colors duration-300"
                                                    onClick={() => decrementItem(product)}> -
                                        </button>
                                        <span className="mx-2.5 text-xl text-[#333]">{product.quantity}</span>
                                        <button className="bg-[#f68b1e] text-white rounded px-2.5 py-1 text-xl
                                        cursor-pointer hover:bg-[#93500d] transition-colors duration-300"
                                                onClick={() => incrementItem(product)}> +
                                        </button>
                                    </div>
                                    <span className="text-[#444]">{product.price.toFixed(2)}</span>
                                </div>
                            </li>
                            ))}
                    </ul>
                    <h2>Total: {totalPrice} VND</h2>
                    <button className="checkout-btn bg-[#f68b1e] text-white text-xl font-medium px-5 py-5 rounded
                    w-[99%] block mx-auto mt-5 cursor-pointer transition-colors duration-300 hover:bg-[#93500d]"
                            onClick={handleCheckout}>Checkout</button>
                </div>
            )}
        </div>
    );
};

export default CartPage;
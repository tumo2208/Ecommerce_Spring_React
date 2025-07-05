import React, {createContext, useContext, useEffect, useReducer} from 'react';

const CartContext = createContext();

const initialState = {
    cart: JSON.parse(localStorage.getItem('cart')) || [],
};

const cartReducer = (state, action) => {
    switch (action.type) {
        case 'ADD_ITEM':
            const existingItem = state.cart.find(item => item.id === action.payload.id);
            let addedCart;
            if (existingItem) {
                addedCart = state.cart.map(item =>
                    item.id === action.payload.id
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                addedCart = [...state.cart, {...action.payload, quantity: 1}];
            }
            localStorage.setItem('cart', JSON.stringify(addedCart));
            return { ...state, cart: addedCart };

        case 'REMOVE_ITEM':
            const removedCart = state.cart.filter(item => item.id !== action.payload.id);
            localStorage.setItem('cart', JSON.stringify(removedCart));
            return { ...state, cart: removedCart };

        case 'INCREMENT_ITEM':
            const incrementedCart = state.cart.map(item =>
                item.id === action.payload.id
                    ? { ...item, quantity: item.quantity + 1 }
                    : item
            );
            localStorage.setItem('cart', JSON.stringify(incrementedCart));
            return { ...state, cart: incrementedCart };

        case 'DECREMENT_ITEM':
            const decrementedCart = state.cart.map(item =>
                item.id === action.payload.id && item.quantity > 1
                    ? { ...item, quantity: item.quantity - 1 }
                    : item
            );
            localStorage.setItem('cart', JSON.stringify(decrementedCart));
            return { ...state, cart: decrementedCart };

        case 'CLEAR_CART':
            localStorage.removeItem('cart');
            return { ...state, cart: [] };
        default:
            return state;
    }
}

export const CartProvider = ({ children }) => {
    const [cartState, cartDispatch] = useReducer(cartReducer, initialState);

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cartState.cart));
    }, [cartState.cart]);

    return (
        <CartContext.Provider value={{ carts: cartState.cart, dispatch: cartDispatch }}>
            {children}
        </CartContext.Provider>
    );
}

export const useCart = () => useContext(CartContext);
import React, {Component} from 'react';
import {Navigation, useLocation} from 'react-router-dom';
import {isAdmin, isAuthenticated} from "./ApiFunction.js";

export const protectedRoute = ({element: Component}) => {
    const location = useLocation();
    return isAuthenticated() ? (
        <Component/>
    ) : (
        <Navigation to="/login" state={{from: location}} replace/>
    );
};

export const adminRoute = ({element: Component}) => {
    const location = useLocation();
    return isAdmin() ? (
        <Component/>
    ) : (
        <Navigation to="/login" state={{from: location}} replace/>
    );
};
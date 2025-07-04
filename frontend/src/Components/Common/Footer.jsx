import React, {useState, useEffect} from 'react';
import {NavLink} from "react-router-dom";
import "../../App.css";

const Footer = () => {
    return (
        <footer className="footer fixed bottom-0 w-full bg-[#333] text-white text-center py-5 z-[1000]">
            <div className="footer-links">
                <ul className="flex justify-center mb-5 p-0 list-none">
                    <NavLink to={"/"}>About Us</NavLink>
                    <NavLink to={"/"}>Contact Us</NavLink>
                    <NavLink to={"/"}>Terms & Conditions</NavLink>
                    <NavLink to={"/"}>Privacy Policy</NavLink>
                    <NavLink to={"/"}>FAQs</NavLink>
                </ul>
            </div>
            <div className="footer-info">
                <p className="text-white m-0">&copy; 2025 Shopee. All right reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;
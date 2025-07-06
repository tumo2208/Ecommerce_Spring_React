import React, {useState, useEffect} from 'react';
import {NavLink, useNavigate} from "react-router-dom";
import {isAdmin, isAuthenticated, logout} from "../../Services/ApiFunction.js";
import "../../App.css";

const Navbar = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();

    const adminStatus = isAdmin();
    const authenticated = isAuthenticated();

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    }

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        if (searchQuery.trim()) {
            navigate(`/?search=${searchQuery}`);
        }
    }

    const handleLogout = () => {
        const confirmLogout = window.confirm("Are you sure you want to logout?");
        if (confirmLogout) {
            logout();
            setTimeout(() => {
                navigate('/login');
            }, 500);
        }
    }

    return (
        <nav className="navbar flex justify-between items-center bg-[#f68b1e] py-2.5 px-5 text-[20px] font-semibold">
            <div className="navbar-brand">
                <NavLink to="/"> <img src="/images/brand.png" alt="Shopee" className="h-10"/></NavLink>
            </div>

            <form className="navbar-search flex items-center" onSubmit={handleSearchSubmit}>
                <input type="text" placeholder="Search Products..." value={searchQuery}
                    className="p-2.5 w-[500px] border-none rounded-sm" onChange={handleSearchChange}/>
                <button type="submit"
                        className="p-2.5 ml-0.5 bg-white text-[#f68b1e] font-bold rounded-sm cursor-pointer">
                    Search
                </button>
            </form>

            <div className="navbar-links flex items-center">
                <NavLink to="/">Home</NavLink>
                <NavLink to="/category">Categories</NavLink>
                {authenticated && <NavLink to="/profile">My Account</NavLink>}
                {adminStatus && <NavLink to="/admin">Admin</NavLink>}
                {!authenticated && <NavLink to="/login">Login</NavLink>}
                {authenticated && <button onClick={handleLogout} className="text-white mx-2.5 font-bold bg-transparent
                border-none cursor-pointer hover:opacity-80">Logout</button>}
                <NavLink to="/cart">Cart</NavLink>
            </div>
        </nav>
    );
};

export default Navbar;
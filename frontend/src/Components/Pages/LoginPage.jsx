import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {login} from "../../Services/ApiFunction.js";

const LoginPage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });
    const [message, setMessage] = useState('');

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await login(formData);
            if (response.status === 200) {
                setMessage('Login successful! Redirecting to home...');
                localStorage.setItem('token', response.token);
                localStorage.setItem('role', response.role);
                setTimeout(() => {
                    navigate('/');
                }, 3000);
            } else {
                setMessage('Login failed. Please try again.');
            }
        } catch (error) {
            console.error('Registration error:', error);
            setMessage('Login failed. Please try again later.');
        }
    }

    return (
        <div className="login-page max-w-[400px] mx-auto mt-12 p-5 border border-[#ddd] rounded-[10px]">
            <h2 className="text-center mb-5">Register</h2>
            {message && <p className="message text-center text-green-600">{message}</p>}
            <form className="flex flex-col" onSubmit={handleLogin}>
                <label>Email: </label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    required/>

                <label>Password: </label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleInputChange}
                    required/>

                <button type="submit" className="p-2.5 bg-[#f68b1e] text-white font-extrabold text-[18px]
                rounded cursor-pointer transition-colors duration-300 hover:bg-[#81480e]">Login
                </button>

                <p className="login-link mt-5 text-sm">
                    Don't have an account? <a href="/register" className="text-[#f68b1e] no-underline">Register</a>
                </p>
            </form>
        </div>
    );
};

export default LoginPage;
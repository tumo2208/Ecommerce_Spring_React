import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {register} from "../../Services/ApiFunction.js";

const RegisterPage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        phoneNumber: '',
    });
    const [message, setMessage] = useState('');

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await register(formData);
            if (response.status === 200) {
                setMessage('Registration successful! Redirecting to login...');
                setTimeout(() => {
                    navigate('/login');
                }, 3000);
            } else {
                setMessage('Registration failed. Please try again.');
            }
        } catch (error) {
            console.error('Registration error:', error);
            setMessage('Registration failed. Please try again later.');
        }
    }

    return (
        <div className="register-page max-w-[400px] mx-auto mt-12 p-5 border border-[#ddd] rounded-[10px]">
            <h2 className="text-center mb-5">Register</h2>
            {message && <p className="message text-center text-green-600">{message}</p>}
            <form className="flex flex-col" onSubmit={handleRegister}>
                <label>Email: </label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    required/>

                <label>Name: </label>
                <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleInputChange}
                    required/>

                <label>Password: </label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleInputChange}
                    required/>

                <label>Phone Number: </label>
                <input
                    type="text"
                    name="phoneNumber"
                    value={formData.phoneNumber}
                    onChange={handleInputChange}
                    required/>

                <button type="submit" className="p-2.5 bg-[#f68b1e] text-white font-extrabold text-[18px]
                rounded cursor-pointer transition-colors duration-300 hover:bg-[#81480e]">Register</button>
                <p className="login-link mt-5 text-sm">
                    Already have an account? <a href="/login" className="text-[#f68b1e] no-underline">Login</a>
                </p>
            </form>
        </div>
    );
};

export default RegisterPage;
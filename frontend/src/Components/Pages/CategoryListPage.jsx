import React, {useState, useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import {getAllCategories} from "../../Services/ApiFunction.js";

const CategoryListPage = () => {
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getAllCategories();
                const data = await response.data || [];
                setCategories(data);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchCategories();
    }, []);

    const handleCategoryClick = (categoryId) => {
        navigate(`/category/${categoryId}`);
    }

    return (
        <div className="category-list max-w-[800px] mx-auto p-5 rounded-lg shadow-[0_4px_80px_rgba(0,9,9,0.01)]">
            {error ? (
                <p className="error-message text-red-600 text-center text-[1.2em] mt-5">{error}</p>
            ) : (
                <div>
                    <h2 className="text-2xl mb-5 text-center text-[#333]">Categories</h2>
                    <ul className="list-none p-0 flex flex-wrap justify-center gap-5">
                        {categories.map(category => (
                            <li key={category.id} className="flex-1 max-w-[calc(33.33%-20px)] basis-[calc(33.33%-20px)]">
                                <button className="w-full py-[15px] text-[1.2rem] bg-[#f68b1e] text-white
                                border-none rounded cursor-pointer transition-colors duration-300 hover:bg-[#d7791b]"
                                        onClick={() => handleCategoryClick(category.id)}>{category.name}</button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default CategoryListPage;
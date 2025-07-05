import React, {useState, useEffect} from 'react';
import {useLocation} from "react-router-dom";
import ProductList from "../Common/ProductList.jsx";
import Pagination from "../Common/Pagination.jsx";
import {getAllProducts, searchProducts} from "../../Services/ApiFunction.js";

const HomePage = () => {
    const location = useLocation();

    const [products, setProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 2;
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                let allProducts = [];
                const queryParams = new URLSearchParams(location.search);
                const searchItem = queryParams.get('search');

                if (searchItem) {
                    const response = await searchProducts(searchItem);
                    allProducts = response.data || [];
                } else {
                    const response = await getAllProducts();
                    allProducts = response.data || [];
                }

                setTotalPages(Math.ceil(allProducts.length / itemsPerPage));
                setProducts(allProducts.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage));
            } catch (error) {
                setError(error.response?.data?.message || error.message
                    || 'An error occurred while fetching products.');
            }
        };

        fetchProducts();
    }, [location.search, currentPage]);

    return (
        <div className="home p-5">
            {error ? (
                <p className="error-message text-center text-red-500 text-[20px] mt-5">{error}</p>
            ) : (
                <div>
                    <ProductList products={products}/>
                    <Pagination currentPage={currentPage} totalPages={totalPages}
                                onPageChange={(page) => setCurrentPage(page)}/>
                </div>
            )}
        </div>
    );
};

export default HomePage;
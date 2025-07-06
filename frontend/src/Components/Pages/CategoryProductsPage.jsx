import React, {useState, useEffect} from 'react';
import {useParams} from "react-router-dom";
import ProductList from "../Common/ProductList.jsx";
import Pagination from "../Common/Pagination.jsx";
import {getProductByCategory} from "../../Services/ApiFunction.js";

const CategoryProductsPage = () => {
    const {categoryId} = useParams();
    const [products, setProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 2;
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCategoryProducts = async () => {
            try {
                const response = await getProductByCategory(categoryId);
                const allProducts = response.data || [];

                setTotalPages(Math.ceil(allProducts.length / itemsPerPage));
                setProducts(allProducts.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage));
            } catch (error) {
                setError(error.message || 'An error occurred while fetching products.');
            }
        };

        fetchCategoryProducts();
    }, [categoryId, currentPage]);

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

export default CategoryProductsPage;
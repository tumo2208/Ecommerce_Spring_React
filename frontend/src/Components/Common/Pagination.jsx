import React from 'react';

const Pagination = ({currentPage, totalPages, onPageChange}) => {
    const pageNumbers = [];
    for (let i = 1; i <= totalPages; i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="pagination flex justify-center my-4 mb-30">
            {pageNumbers.map((number) => (
                <button key={number} onClick={() => onPageChange(number)}
                        className={number === currentPage ? `active bg-[#f68b1e] text-white px-5 py-2 mx-1 rounded 
                        transition-colors duration-300 hover:bg-[#874f17] ` : `bg-[#555] text-white px-5 py-2 mx-1 
                        rounded transition-colors duration-300 hover:bg-[#874f17]`}>{number}</button>
            ))}
        </div>
    );
};

export default Pagination;
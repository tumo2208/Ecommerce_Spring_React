import axios from 'axios';

export const api = axios.create({
    baseURL: 'http://localhost:3001',
});

function getHeaders() {
    const token = localStorage.getItem('token');
    return {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
    }
}

export async function register(registrationData) {
    try {
        const response = await api.post('/auth/register', registrationData);
        return response.data;
    } catch (error) {
        console.error('Registration error:', error);
        throw error;
    }
}

export async function login(loginData) {
    try {
        const response = await api.post('/auth/login', loginData);
        return response.data;
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
}

export function logout() {
    localStorage.removeItem('token');
}

export function isAuthenticated() {
    const token = localStorage.getItem('token');
    return !!token;
}

export function isAdmin() {
    const userRole = localStorage.getItem('role');
    return userRole === 'ADMIN';
}

export async function getUserProfile() {
    try {
        const response = await api.get('/user/profile', {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Get user profile error:', error);
        throw error;
    }
}

export async function saveAddress(addressData) {
    try {
        const response = await api.post('/address/save', addressData, {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Save address error:', error);
        throw error;
    }
}

export async function addCategory(categoryName) {
    try {
        const response = await api.post('/category/create', {
            params: categoryName,
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Add category error:', error);
        throw error;
    }
}

export async function updateCategory(categoryId, categoryName) {
    try {
        const response = await api.put(`/category/update/${categoryId}`, {
            params: categoryName,
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Update category error:', error);
        throw error;
    }
}

export async function deleteCategory(categoryId) {
    try {
        const response = await api.delete(`/category/delete/${categoryId}`, {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Delete category error:', error);
        throw error;
    }
}

export async function getCategoryById(categoryId) {
    try {
        const response = await api.get(`/category/${categoryId}`);
        return response.data;
    } catch (error) {
        console.error('Get category by ID error:', error);
        throw error;
    }
}

export async function getAllCategories() {
    try {
        const response = await api.get('/category/all');
        return response.data;
    } catch (error) {
        console.error('Get all categories error:', error);
        throw error;
    }
}

export async function addProduct(productData) {
    try {
        const response = await api.post('/product/create', productData, {
            headers: {
                ...getHeaders(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    } catch (error) {
        console.error('Add product error:', error);
        throw error;
    }
}

export async function updateProduct(productId, productData) {
    try {
        const response = await api.put(`/product/update/${productId}`, productData, {
            headers: {
                ...getHeaders(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    } catch (error) {
        console.error('Update product error:', error);
        throw error;
    }
}

export async function deleteProduct(productId) {
    try {
        const response = await api.delete(`/product/delete/${productId}`, {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Delete product error:', error);
        throw error;
    }
}

export async function getAllProducts() {
    try {
        const response = await api.get('/product/all');
        return response.data;
    } catch (error) {
        console.error('Get all products error:', error);
        throw error;
    }
}

export async function getProductByCategory(categoryId) {
    try {
        const response = await api.get(`/product/category/${categoryId}`);
        return response.data;
    } catch (error) {
        console.error('Get product by ID error:', error);
        throw error;
    }
}

export async function searchProducts(query) {
    try {
        const response = await api.get(`/product/search`, {
            params: query
        });
        return response.data;
    } catch (error) {
        console.error('Search products error:', error);
        throw error;
    }
}

export async function placeOrder(orderData) {
    try {
        const response = await api.post('/order/place', orderData, {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Place order error:', error);
        throw error;
    }
}

export async function updateOrderStatus(orderId, status) {
    try {
        const response = await api.put(`/order/update-status/${orderId}`, {
            params: status,
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Update order status error:', error);
        throw error;
    }
}

export async function getAllOrders() {
    try {
        const response = await api.get('/order/filter', {
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Get all orders error:', error);
        throw error;
    }
}

export async function getOrderById(itemId) {
    try {
        const response = await api.get(`/order/filter`, {
            params: itemId,
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Get order by ID error:', error);
        throw error;
    }
}

export async function getOrderByStatus(status) {
    try {
        const response = await api.get(`/order/filter`, {
            params: { status },
            headers: getHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Get order by status error:', error);
        throw error;
    }
}







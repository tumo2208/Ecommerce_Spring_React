import './App.css'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {CartProvider} from "./Components/Context/CartContext.jsx";
import Navbar from "./Components/Common/Navbar.jsx";
import Footer from "./Components/Common/Footer.jsx";
import HomePage from "./Components/Pages/HomePage.jsx";
import ProductDetailPage from "./Components/Pages/ProductDetailPage.jsx";
import CategoryListPage from "./Components/Pages/CategoryListPage.jsx";
import CategoryProductsPage from "./Components/Pages/CategoryProductsPage.jsx";
import CartPage from "./Components/Pages/CartPage.jsx";
import RegisterPage from "./Components/Pages/RegisterPage.jsx";
import LoginPage from "./Components/Pages/LoginPage.jsx";

function App() {

  return (
    <CartProvider>
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/' element={<HomePage/>}></Route>
        <Route path='/product/:productId' element={<ProductDetailPage/>}></Route>
        <Route path='/category' element={<CategoryListPage/>}></Route>
        <Route path='/category/:categoryId' element={<CategoryProductsPage/>}></Route>
        <Route path='/cart' element={<CartPage/>}></Route>
        <Route path='/register' element={<RegisterPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
    </CartProvider>
  )
}

export default App

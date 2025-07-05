import './App.css'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {CartProvider} from "./Components/Context/CartContext.jsx";
import Navbar from "./Components/Common/Navbar.jsx";
import Footer from "./Components/Common/Footer.jsx";
import HomePage from "./Components/Pages/HomePage.jsx";

function App() {

  return (
    <CartProvider>
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/' element={<HomePage/>}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
    </CartProvider>
  )
}

export default App

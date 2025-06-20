import { useState } from 'react'
import './App.css'
import Navigation from "./Customer/Components/Navigation/Navigation.jsx";
import './index.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Navigation></Navigation>
    </>
  )
}

export default App

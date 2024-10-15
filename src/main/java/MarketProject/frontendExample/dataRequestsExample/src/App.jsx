import { useEffect, useState } from 'react'
import axios from 'axios'
import './App.css'

function App() {

  const BASE_URL = "http://localhost:8085"


  const getAllProducts = async () => {
    const response = await axios.get(BASE_URL + "/seller/products")
    console.log(response)
  }


  useEffect(() => {

    getAllProducts();

  },);



  return (

    <div>


      <div></div>





    </div>
  )
}

export default App

import React, { useState, useRef } from 'react'
import { asset } from '../../assets/asset'
import { addFood } from '../../service/foodService'
import { toast } from 'react-toastify'

const AddFood = () => {
  const [image, setImage] = useState(null)
  const [data, setData] = useState({
    name: '',
    description: '',
    price: '',
    category: 'Biryani',
  })

  const fileInputRef = useRef(null)

  const onChangeHandler = (event) => {
    const name = event.target.name
    const value = event.target.value
    setData((data) => ({ ...data, [name]: value }))
  }

  const onSubmithandler = async (event) => {
    event.preventDefault()

    if (!image) {
      toast.error('Image is required')
      return
    }
    try {
      await addFood(data, image)
      toast.success('Food added successfully')

      setData({
        name: '',
        description: '',
        price: '',
        category: 'Biryani',
      })
      setImage(null)
      if (fileInputRef.current) {
        fileInputRef.current.value = ''
      }
    } catch (error) {
      toast.error('Error adding food')
    }
  }

  return (
    <div className="mx-2 mt-2 ">
      <div className="row ">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-1">Add Food</h2>
            <form onSubmit={onSubmithandler}>
              <div className="mb-3">
                <img
                  src={image ? URL.createObjectURL(image) : asset.logo}
                  alt="preview"
                  width={120}
                  className="img-fluid mt-3"
                />
                <br />
                <label htmlFor="formFile" className="form-label">
                  Upload Image
                </label>
                <input
                  className="form-control"
                  type="file"
                  id="formFile"
                  accept="image/*"
                  ref={fileInputRef}
                  onChange={(e) => setImage(e.target.files[0])}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">
                  Name
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  required
                  name="name"
                  value={data.name}
                  onChange={onChangeHandler}
                  placeholder='Enter name of food'
                />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  Description
                </label>
                <textarea
                  className="form-control"
                  id="description"
                  placeholder='Write here.. '
                  rows="5"
                  required
                  name="description"
                  value={data.description}
                  onChange={onChangeHandler}
                ></textarea>
              </div>

              <div className="mb-3">
                <label htmlFor="category"  className="form-label">
                  Category
                </label>
                <select
                  name="category"
                  id="category"
                  className="form-control"
                  
                  value={data.category}
                  onChange={onChangeHandler}
                >
                  <option value="Biryani">Biryani</option>
                  <option value="Cake">Cake</option>
                  <option value="Pizza">Pizza</option>
                  <option value="Rolls">Rolls</option>
                  <option value="Salad">Salad</option>
                  <option value="Ice cream">Ice cream</option>
                  <option value="Burger">Burger</option>
                </select>
              </div>

              <div className="mb-3">
                <label htmlFor="price" className="form-label">
                  Price
                </label>
                <input
                  type="number"
                  name="price"
                  className="form-control"
                  id="price"
                  placeholder='Enter price'
                  value={data.price}
                  onChange={onChangeHandler}
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Save
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AddFood

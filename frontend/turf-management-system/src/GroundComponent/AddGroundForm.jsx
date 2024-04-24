import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const AddGroundForm = () => {
  let navigate = useNavigate();

  const [selectedImage, setSelectedImage] = useState(null);

  const [ground, setGround] = useState({
    name: "",
    description: "",
    width: "",
    height: "",
    price: "",
    length: "",
  });

  const handleInput = (e) => {
    setGround({ ...ground, [e.target.name]: e.target.value });
  };

  const saveGround = () => {
    const formData = new FormData();
    formData.append("image", selectedImage);
    formData.append("name", ground.name);
    formData.append("description", ground.description);
    formData.append("width", ground.width);
    formData.append("height", ground.height);
    formData.append("price", ground.price);
    formData.append("length", ground.length);

    axios
      .post("http://localhost:8080/api/ground/add", formData)
      .then((result) => {
      console.log("result", result);
      result.json().then((res) => {
        console.log(res);

        if (res.success) {
          console.log("Got the success response");

          toast.success(res.responseMessage, {
            position: "top-center",
            autoClose: 2000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });

          setTimeout(() => {
            navigate("/home");
          }, 3000); // Redirect after 3 seconds
        } else {
          console.log("Didn't got success response");
          toast.error("It seems server is down", {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          setTimeout(() => {
            window.location.reload(true);
          }, 1000); // Redirect after 3 seconds
        }
      });
      });
  };

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center">
        <div
          className="card form-card border-color custom-bg"
          style={{ width: "50rem" }}
        >
          <div className="card-header bg-color custom-bg-text text-center">
            <h5 className="card-title">Add Ground</h5>
          </div>
          <div className="card-body text-color">
            <form className="row g-3">
              <div className="col-md-6 mb-3">
                <label htmlFor="name" className="form-label">
                  <b>Ground Name</b>
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  onChange={handleInput}
                  value={ground.name}
                />
              </div>
              <div className="col-md-6 mb-3">
                <label htmlFor="description" className="form-label">
                  <b>Ground Description</b>
                </label>
                <textarea
                  className="form-control"
                  id="description"
                  name="description"
                  rows="3"
                  onChange={handleInput}
                  value={ground.description}
                />
              </div>

              <div className="col-md-6 mb-3 mt-1">
                <label htmlFor="quantity" className="form-label">
                  <b>Ground width</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="width"
                  name="width"
                  onChange={handleInput}
                  value={ground.width}
                />
              </div>

              <div className="col-md-6 mb-3 mt-1">
                <label htmlFor="quantity" className="form-label">
                  <b>Ground Length</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="length"
                  name="length"
                  onChange={handleInput}
                  value={ground.length}
                />
              </div>

              <div className="col-md-6 mb-3 mt-1">
                <label htmlFor="quantity" className="form-label">
                  <b>Ground Height</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="height"
                  name="height"
                  onChange={handleInput}
                  value={ground.height}
                />
              </div>

              <div className="col-md-6 mb-3 mt-1">
                <label htmlFor="quantity" className="form-label">
                  <b>Price</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="price"
                  name="price"
                  onChange={handleInput}
                  value={ground.price}
                />
              </div>

              <div className="col-md-6 mb-3">
                <label htmlFor="image1" className="form-label">
                  <b> Select Ground Image</b>
                </label>
                <input
                  className="form-control"
                  type="file"
                  id="image"
                  name="image"
                  value={ground.image}
                  onChange={(e) => setSelectedImage(e.target.files[0])}
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center">
                <button
                  type="submit"
                  className="btn bg-color custom-bg-text"
                  onClick={saveGround}
                >
                  Add Ground
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddGroundForm;

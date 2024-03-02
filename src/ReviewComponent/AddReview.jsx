import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import GroundCarousel from "../GroundComponent/GroundCarousel";
import { useLocation } from "react-router-dom";

const AddReview = () => {
  let user = JSON.parse(sessionStorage.getItem("active-customer"));

  const [userId, setUserId] = useState(user.id);

  const [star, setStar] = useState("");
  const [review, setReview] = useState("");

  const location = useLocation();
  const ground = location.state;
  const [groundId, setGroundId] = useState(ground.id);

  let navigate = useNavigate();

  const saveReview = (e) => {
    if (user == null) {
      e.preventDefault();
      alert("Please login as Customer for adding your review!!!");
    } else {
      e.preventDefault();
      setUserId(user.id);
      let data = { userId, groundId, star, review };

      fetch("http://localhost:8080/api/ground/review/add", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      }).then((result) => {
        result.json().then((res) => {
          console.log(res);
          navigate("/book/ground/" + ground.id);
          console.log(res.responseMessage);
          toast.warn(res.responseMessage, {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        });
      });
    }
  };

  return (
    <div className="container-fluid mb-5">
      <div class="row">
        <div class="col-sm-2 mt-2"></div>
        <div class="col-sm-3 mt-2">
          <div class="card form-card border-color custom-bg">
            <GroundCarousel
              item={{
                image: ground.image,
              }}
            />
          </div>
        </div>

        <div class="col-sm-5 mt-2">
          <div
            className="card form-card border-color custom-bg"
            style={{ width: "30rem" }}
          >
            <div className="card-header bg-color text-center custom-bg-text">
              <h5 className="card-title">Add Ground Review</h5>
            </div>
            <div className="card-body text-color">
              <form onSubmit={saveReview}>
                <div className="mb-3">
                  <label className="form-label">
                    <b>Star</b>
                  </label>

                  <select
                    name="locationId"
                    onChange={(e) => {
                      setStar(e.target.value);
                    }}
                    className="form-control"
                  >
                    <option value="">Select Star</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                </div>
                <div className="mb-3">
                  <label htmlFor="review" className="form-label">
                    <b>Ground Review</b>
                  </label>
                  <textarea
                    className="form-control"
                    id="review"
                    rows="3"
                    placeholder="enter review.."
                    onChange={(e) => {
                      setReview(e.target.value);
                    }}
                    value={review}
                  />
                </div>

                <input
                  type="submit"
                  className="btn bg-color custom-bg-text"
                  value="Add Review"
                />

                <ToastContainer />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddReview;

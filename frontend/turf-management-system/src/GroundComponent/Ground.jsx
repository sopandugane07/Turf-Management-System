import { useParams } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import GroundCard from "./GroundCard";
import GetReviews from "../ReviewComponent/GetReviews";

const Ground = () => {
  const { groundId } = useParams();

  let user = JSON.parse(sessionStorage.getItem("active-customer"));
  let admin = JSON.parse(sessionStorage.getItem("active-admin"));

  const [timeSlots, setTimeSlots] = useState([]);

  const [grounds, setGrounds] = useState([]);

  let navigate = useNavigate();

  const [ground, setGround] = useState({
    id: "",
    name: "",
    description: "",
    price: "",
    width: "",
    height: "",
    image: "",
  });

  const [booking, setBooking] = useState({
    userId: "",
    groundId: "",
    date: "",
    timeSlot: "",
  });

  if (user) {
    booking.userId = user.id;
  }

  booking.groundId = groundId;

  const handleBookingInput = (e) => {
    setBooking({ ...booking, [e.target.name]: e.target.value });
  };

  const retrieveAllSlots = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/book/ground/fetch/slots"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllGrounds = async () => {
      const allGrounds = await retrieveAllGrounds();
      if (allGrounds) {
        setGrounds(allGrounds.grounds);
      }
    };

    getAllGrounds();
  }, []);

  const retrieveAllGrounds = async () => {
    const response = await axios.get("http://localhost:8080/api/ground/fetch");

    return response.data;
  };

  const retrieveGround = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/ground/id?groundId=" + groundId
    );

    return response.data;
  };

  useEffect(() => {
    const getGround = async () => {
      const retrievedGround = await retrieveGround();

      setGround(retrievedGround.grounds[0]);
    };

    const getAllSlots = async () => {
      const allSlots = await retrieveAllSlots();
      if (allSlots) {
        setTimeSlots(allSlots);
      }
    };

    getGround();
    getAllSlots();
  }, [groundId]);

  const bookGround = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/book/ground/", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(booking),
    }).then((result) => {
      console.log("result", result);
      result.json().then((res) => {
        console.log(res);

        if (res.success) {
          console.log("Got the success response");

          toast.success(res.responseMessage, {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });

          setTimeout(() => {
            navigate("/user/ground/bookings");
          }, 1000); // Redirect after 3 seconds
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

  const navigateToAddReviewPage = () => {
    navigate("/ground/review/add", { state: ground });
  };

  return (
    <div className="container-fluid mb-5">
      <div class="row">
        <div class="col-sm-3 mt-2">
          <div class="card form-card border-color custom-bg">
            <img
              src={"http://localhost:8080/api/ground/" + ground.image}
              style={{
                maxHeight: "500px",
                maxWidth: "100%",
                width: "auto",
              }}
              class="card-img-top rounded mx-auto d-block m-2"
              alt="img"
            />
          </div>
        </div>
        <div class="col-sm-6 mt-2">
          <div class="card form-card border-color custom-bg">
            <div class="card-header bg-color">
              <div className="d-flex justify-content-between">
                <h1 className="custom-bg-text">{ground.name}</h1>
              </div>
            </div>

            <div class="card-body text-left text-color">
              <div class="text-left mt-3">
                <h3>Description :</h3>
              </div>
              <h4 class="card-text">{ground.description}</h4>

              <div class="text-left mt-3 h4">
                <b>Width : {ground.width} ft</b>
                <b className="ms-3">Length : {ground.length} ft</b>
                <b className="ms-3">Height : {ground.height} ft</b>
              </div>
              <div class="text-left mt-3">
                <h5 className="text-danger">
                  Note: The price mentioned below are for 1 hour
                </h5>
              </div>

              <div class="text-left mt-3"></div>
            </div>

            <div class="card-footer custom-bg">
              <div className="d-flex justify-content-center">
                <p>
                  <span>
                    <h4>Price : &#8377;{ground.price}</h4>
                  </span>
                </p>
              </div>

              <div>
                <form class="row g-3" onSubmit={bookGround}>
                  <div class="col-auto">
                    <label for="date">Booking Date</label>
                    <input
                      type="date"
                      class="form-control"
                      id="date"
                      name="date"
                      onChange={handleBookingInput}
                      value={booking.checkIn}
                      required
                    />
                  </div>
                  <div class="col-auto">
                    <label for="date">Booking Time Slot</label>
                    <select
                      name="timeSlot"
                      onChange={handleBookingInput}
                      className="form-control"
                    >
                      <option value="">Select Time Slot</option>

                      {timeSlots.map((slot) => {
                        return <option value={slot}> {slot} </option>;
                      })}
                    </select>
                  </div>

                  <div className="d-flex justify-content-center ">
                    <div>
                      <input
                        type="submit"
                        class="btn btn-lg bg-color custom-bg-text mt-3 mb-3"
                        value="Book Ground"
                      />
                    </div>
                  </div>
                </form>
              </div>

              {(() => {
                if (user) {
                  console.log(user);
                  return (
                    <div>
                      <input
                        type="submit"
                        className="btn bg-color custom-bg-text mb-3"
                        value="Add Review"
                        onClick={navigateToAddReviewPage}
                      />
                    </div>
                  );
                }
              })()}
            </div>
          </div>
        </div>

        <div class="col-sm-3 mt-2">
          <GetReviews />
        </div>
      </div>

      <div className="row mt-4">
        <div className="col-sm-12">
          <h2>Other Grounds:</h2>
          <div className="row row-cols-1 row-cols-md-4 g-4">
            {grounds.map((g) => {
              return <GroundCard item={g} />;
            })}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Ground;

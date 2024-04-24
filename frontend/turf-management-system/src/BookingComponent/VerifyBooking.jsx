import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const VerifyBooking = () => {
  const [booking, setBooking] = useState([]);

  const { bookingId } = useParams();
  const [bookingStatus, setBookingStatus] = useState([]);

  const [updateBookingStatus, setUpdateBookingStatus] = useState({
    bookingId: "",
    status: "",
  });

  updateBookingStatus.bookingId = bookingId;

  const retrieveAllBookingStatus = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/book/ground/fetch/status"
    );
    return response.data;
  };

  useEffect(() => {
    const getBooking = async () => {
      const b = await retrieveBooking();
      if (b) {
        setBooking(b.bookings[0]);
      }
    };

    const getAllBookingStatus = async () => {
      const allBookingStatus = await retrieveAllBookingStatus();
      if (allBookingStatus) {
        setBookingStatus(allBookingStatus);
      }
    };

    getAllBookingStatus();
    getBooking();
  }, []);

  const retrieveBooking = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/book/ground/fetch/bookingId?id=" + bookingId
    );
    console.log(response.data);
    return response.data;
  };

  const handleBookingInput = (e) => {
    setUpdateBookingStatus({
      ...updateBookingStatus,
      [e.target.name]: e.target.value,
    });
  };

  let navigate = useNavigate();

  const updateGroundBookingStatus = (e) => {
    e.preventDefault();

    console.log(updateBookingStatus);

    fetch("http://localhost:8080/api/book/ground/update/status", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updateBookingStatus),
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
            navigate("/user/ground/booking/all");
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

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center mb-5">
        <div
          className="card form-card border-color custom-bg"
          style={{ width: "25rem" }}
        >
          <div className="card-header bg-color custom-bg-text text-center">
            <h5 className="card-title">Booking</h5>
          </div>
          <div className="card-body text-color">
            <div>
              <img
                src={"http://localhost:8080/api/ground/" + booking.groundImage}
                class="img-fluid"
                alt="ground_img"
              ></img>
            </div>

            <div className="mb-3 mt-3">
              <label htmlFor="quantity" className="form-label">
                <b>Ground Name</b>
              </label>
              <input
                type="text"
                className="form-control"
                value={booking.groundName}
                required
                readOnly
              />
            </div>

            <div className="mb-3 mt-3">
              <label htmlFor="quantity" className="form-label">
                <b>Time Slot</b>
              </label>
              <input
                type="text"
                className="form-control"
                value={booking.timeSlot}
                required
                readOnly
              />
            </div>

            <div className="mb-3 mt-3">
              <label htmlFor="quantity" className="form-label">
                <b>Booking Id</b>
              </label>
              <input
                type="text"
                className="form-control"
                value={booking.bookingId}
                required
                readOnly
              />
            </div>

            <div className="mb-3 mt-1">
              <form>
                <div class="col">
                  <label htmlFor="quantity" className="form-label">
                    <b>Select Status</b>
                  </label>
                  <select
                    name="status"
                    onChange={handleBookingInput}
                    className="form-control"
                  >
                    <option value="">Status</option>

                    {bookingStatus.map((status) => {
                      return <option value={status}> {status} </option>;
                    })}
                  </select>
                </div>

                <div class="col">
                  <button
                    type="submit"
                    class="btn btn-sm bg-color custom-bg-text mt-4"
                    onClick={updateGroundBookingStatus}
                  >
                    Update
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VerifyBooking;

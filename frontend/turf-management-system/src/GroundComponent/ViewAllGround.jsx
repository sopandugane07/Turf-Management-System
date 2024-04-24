import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { ToastContainer, toast } from "react-toastify";

const ViewAllGround = () => {
  const [allGround, setAllGround] = useState([]);

  useEffect(() => {
    const getAllGround = async () => {
      const allGround = await retrieveAllGround();
      if (allGround) {
        setAllGround(allGround.grounds);
      }
    };

    getAllGround();
  }, []);

  const retrieveAllGround = async () => {
    const response = await axios.get("http://localhost:8080/api/ground/fetch");
    console.log(response.data);
    return response.data;
  };

  const deleteGround = (groundId) => {
    fetch("http://localhost:8080/api/ground/delete?groundId=" + groundId, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((result) => {
        result.json().then((res) => {
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
          } else {
            console.log("Failed to delete the employee");
            toast.error("It seems server is down", {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
          }
        });
      })
      .catch((error) => {
        console.error(error);
        toast.error("It seems server is down", {
          position: "top-center",
          autoClose: 1000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      });

    setTimeout(() => {
      window.location.reload(true);
    }, 2000); // Reload after 3 seconds 3000
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 custom-bg border-color "
        style={{
          height: "45rem",
        }}
      >
        <div className="card-header custom-bg-text text-center bg-color">
          <h2>All Ground</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color bg-color custom-bg-text">
                <tr>
                  <th scope="col">Ground</th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Ground Width</th>
                  <th scope="col">Ground Height</th>
                  <th scope="col">Ground Length</th>
                  <th scope="col">Price</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {allGround.map((ground) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={
                            "http://localhost:8080/api/ground/" + ground.image
                          }
                          class="card-img-top rounded mx-auto d-block m-2"
                          alt="img"
                          style={{
                            width: "200px",
                            height: "auto",
                          }}
                        />
                      </td>
                      <td>
                        <b>{ground.name}</b>
                      </td>

                      <td>
                        <b>{ground.description}</b>
                      </td>
                      <td>
                        <b>{ground.width}</b>
                      </td>
                      <td>
                        <b>{ground.height}</b>
                      </td>
                      <td>
                        <b>{ground.length}</b>
                      </td>
                      <td>
                        <b>{ground.price}</b>
                      </td>
                      <td>
                        <button
                          onClick={() => deleteGround(ground.id)}
                          className="btn btn-sm bg-color custom-bg-text"
                        >
                          Remove
                        </button>
                        <ToastContainer />
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAllGround;

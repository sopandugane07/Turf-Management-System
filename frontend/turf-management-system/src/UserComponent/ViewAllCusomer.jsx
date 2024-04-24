import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";

const ViewAllCustomer = () => {
  const [allCustomer, setAllCustomer] = useState([]);

  useEffect(() => {
    const getAllCustomer = async () => {
      const allCustomer = await retrieveAllCustomer();
      if (allCustomer) {
        setAllCustomer(allCustomer.users);
      }
    };

    getAllCustomer();
  }, []);

  const retrieveAllCustomer = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/user/customer/all"
    );
    console.log(response.data);
    return response.data;
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
          <h2>All Customers</h2>
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
                  <th scope="col">First Name</th>
                  <th scope="col">Last Name</th>
                  <th scope="col">Email Id</th>
                  <th scope="col">Phone No</th>
                  <th scope="col">Address</th>
                  
                </tr>
              </thead>
              <tbody>
                {allCustomer.map((customer) => {
                  return (
                    <tr>
                      <td>
                        <b>{customer.firstName}</b>
                      </td>
                      
                      <td>
                        <b>{customer.lastName}</b>
                      </td>
                      <td>
                        <b>{customer.emailId}</b>
                      </td>
                      <td>
                        <b>{customer.contact}</b>
                      </td>
                      
                      <td>
                        <b>{customer.street+" "+customer.city+" "+customer.pincode}</b>
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

export default ViewAllCustomer;

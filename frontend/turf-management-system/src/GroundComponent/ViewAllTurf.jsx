import Carousel from "../page/Carousel";
import axios from "axios";
import { useState, useEffect } from "react";
import GroundCard from "./GroundCard";

const ViewAllTurf = () => {
  const [grounds, setGrounds] = useState([]);

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

  return (
    <div className="container-fluid mb-2">
      <Carousel />

      <div className="mt-2 mb-5">
        <div className="row mt-4">
          <div className="col-sm-12">
            <div className="row row-cols-1 row-cols-md-4 g-4">
              {grounds.map((ground) => {
                return <GroundCard item={ground} />;
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAllTurf;

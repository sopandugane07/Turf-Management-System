import AdminHeader from "./AdminHeader";
import CustomerHeader from "./CustomerHeader";
import NormalHeader from "./NormalHeader";

const RoleNav = () => {
  const user = JSON.parse(sessionStorage.getItem("active-customer"));
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));

  if (user !== null) {
    return <CustomerHeader />;
  } else if (admin !== null) {
    return <AdminHeader />;
  } else {
    return <NormalHeader />;
  }
};

export default RoleNav;

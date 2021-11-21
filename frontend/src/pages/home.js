import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import axios from "../api/axiosConfig";
import Sidebar from "../components/home/Sidebar";
import UnregisteredSidebar from "../components/home/UnregisteredSidebar";
import UnregisteredUserContext from "../context/unregisteredUser";
import UserContext from "../context/user";
import * as ROUTES from "../constants/routes";

export default function Home() {
  const { userToken } = useContext(UserContext);
  const { unregisteredUser, setUnregisteredUser } = useContext(
    UnregisteredUserContext
  );

  const [error, setError] = useState("");

  useEffect(() => {
    // make this execute only once
    if (!unregisteredUser) {
      // if there aren't unregistered user credentials, ask for some
      async function fetchData() {
        try {
          const response = await axios.get("/unregistered-users");
          setUnregisteredUser(response.data);
        } catch (error) {
          setError(error);
        }
      }
      fetchData();
    }
  }, [unregisteredUser, setUnregisteredUser]);

  useEffect(() => {
    document.title = "No Time for Heroes";
  });

  return (
    <span>
      <h1>Home</h1>
      {userToken ? (
        <>
          <Sidebar />
          <Link to={ROUTES.CREATE_GAME}>
            <Button type="submit">
              Create game
            </Button>
          </Link>
          <br />
        </>
      ) : (
        <UnregisteredSidebar />
      )}
      <Link to={ROUTES.BROWSE_GAMES}>
        <Button type="submit">
          Browse games
        </Button>
      </Link>
    </span>
  );
}

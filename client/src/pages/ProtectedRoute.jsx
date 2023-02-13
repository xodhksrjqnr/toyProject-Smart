import React, { useContext } from 'react';
import { UserContext } from '../context/UserContext';
import { Navigate } from 'react-router-dom';
import Cookies from 'universal-cookie';

export default function ProtectedRoute({ children }) {
  const { user } = useContext(UserContext);
  const cookies = new Cookies();

  if (!user.state && Boolean(cookies.get('id'))) {
    return children;
  } else if (!user.state) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

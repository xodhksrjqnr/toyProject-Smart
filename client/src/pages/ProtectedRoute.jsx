import React, { useContext } from 'react';
import { UserContext } from '../context/UserContext';
import { Navigate } from 'react-router-dom';

export default function ProtectedRoute({ children }) {
  const { user } = useContext(UserContext);
  if (!user.state) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

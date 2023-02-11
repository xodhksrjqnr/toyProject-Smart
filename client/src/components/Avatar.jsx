import React from 'react';
import { Link } from 'react-router-dom';

export default function Avatar({ user }) {
  return <Link to="/myorder">{user.id}님</Link>;
}

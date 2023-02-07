import React from 'react';
import { Link } from 'react-router-dom';
import { MdAdminPanelSettings } from 'react-icons/md';

export default function AdminButton() {
  return (
    <button>
      <Link to="/admin">
        <MdAdminPanelSettings className="text-white text-2xl" />
      </Link>
    </button>
  );
}

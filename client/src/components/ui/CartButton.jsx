import React from 'react';
import { Link } from 'react-router-dom';
import { BsCartPlusFill } from 'react-icons/bs';

export default function CartButton() {
  return (
    <button>
      <Link to="/carts">
        <BsCartPlusFill className="text-white text-2xl" />
      </Link>
    </button>
  );
}

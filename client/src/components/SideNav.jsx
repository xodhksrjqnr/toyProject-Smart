import React, { useState } from 'react';
import { BsCaretDownFill } from 'react-icons/bs';
import { categories } from '../constants/categories';

export default function SideNav() {
  const handleClick = (e) => {
    e.target.nextSibling.classList.toggle('hidden');
    e.target.querySelector('svg').classList.toggle('rotate-180');
  };
  return (
    <nav className="w-36 text-left h-screen bg-gray-50 px-2 py-4 drop-shadow-md">
      {categories.map((category) => (
        <div className="bg-white first:rounded-t-lg last:rounded-b-lg border-b drop-shadow-md">
          <button
            className="flex justify-between items-center text-sm font-bold w-full p-2"
            onClick={handleClick}
          >
            {category.main}
            <BsCaretDownFill className="pointer-events-none text-blue-800 text-xs" />
          </button>
          <div className="hidden">
            <ul className="py-2 bg-gray-50">
              {category.subdivision.map((item) => (
                <li className="text-xs text-slate-800 mb-2 pl-2 ">{item}</li>
              ))}
            </ul>
          </div>
        </div>
      ))}
    </nav>
  );
}

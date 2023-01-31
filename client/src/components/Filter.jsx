import React, { useState } from 'react';
import { filterList } from '../constants/filterList';
import FilterItem from './FilterItem';

export default function Filter({ onClick }) {
  const [selected, setSelected] = useState(filterList[0].title);

  return (
    <div className="flex text-xs my-2 border-2 border-blue-400">
      <span className="bg-gray-200 p-1">정렬</span>
      <ul className="flex  p-1 cursor-pointer">
        {filterList.map((item, index) => (
          <FilterItem
            key={index}
            onClick={onClick}
            onFilterChange={setSelected}
            selected={selected}
            item={item}
          />
        ))}
      </ul>
    </div>
  );
}

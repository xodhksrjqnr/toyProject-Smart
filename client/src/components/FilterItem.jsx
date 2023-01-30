import React from 'react';

export default function FilterItem({
  onClick,
  onFilterChange,
  selected,
  item,
}) {
  return (
    <li
      className={`${selected === item.title && 'font-bold'} px-2`}
      onClick={() => {
        onClick(item.value);
        onFilterChange(item.title);
      }}
    >
      {item.title}
    </li>
  );
}

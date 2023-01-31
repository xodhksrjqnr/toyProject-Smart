import React from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';

export default function PageButton({ text, onClickPage, info, state, move }) {
  const { page, first, last } = info;
  const navigate = useNavigate();

  return (
    <li
      className={`${page === text - 1 && 'font-bold'} ${
        first && 'first:invisible'
      } ${page === 0 && 'first:invisible'} ${last && 'last:invisible'}
       border-2 border-blue-600 w-8 h-8 bg-white mx-1 rounded-md flex justify-center items-center cursor-pointer`}
      onClick={() => {
        if (move === 'prev') onClickPage(page - 1);
        else if (move === 'next') onClickPage(page + 1);
        else {
          onClickPage(text - 1);
          navigate({
            pathname: '',
            search: `?keyword=${state}&page=${text}`,
          });
        }
      }}
    >
      {text}
    </li>
  );
}

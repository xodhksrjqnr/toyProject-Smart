import React from 'react';
import { AiOutlineLeft, AiOutlineRight } from 'react-icons/ai';
import PageButton from './PageButton';

export default function Pagenation({
  totalPages,
  page,
  first,
  last,
  onClickPage,
  state,
}) {
  const info = { page, first, last };
  const pages = [...new Array(totalPages)].map((_, i) => i + 1);

  return (
    <ol className="flex justify-center items-center m-8">
      <PageButton
        text={<AiOutlineLeft />}
        onClickPage={onClickPage}
        info={info}
        state={state}
        move="prev"
      />
      {pages.map((num) => (
        <PageButton
          key={num}
          text={num}
          onClickPage={onClickPage}
          info={info}
          state={state}
        />
      ))}
      <PageButton
        text={<AiOutlineRight />}
        onClickPage={onClickPage}
        info={info}
        state={state}
        move="next"
      />
    </ol>
  );
}

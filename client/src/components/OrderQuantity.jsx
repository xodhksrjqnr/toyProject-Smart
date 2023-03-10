import React, { useEffect, useState } from 'react';
import { AiOutlinePlusSquare, AiOutlineMinusSquare } from 'react-icons/ai';

export default function OrderQuantity({
  onPlus,
  onMinus,
  size,
  onChangeQuantity,
}) {
  const [count, setCount] = useState(1);

  const handleMinus = () => {
    if (count < 2) return;
    setCount((prev) => prev - 1);
    onMinus();
  };
  const handlePlus = () => {
    setCount((prev) => prev + 1);
    onPlus();
  };
  useEffect(() => {
    onChangeQuantity(size, count);
  }, [count]);

  return (
    <>
      <AiOutlineMinusSquare onClick={handleMinus} className="text-red-600" />
      <span className="bg-white px-1">{count}</span>
      <AiOutlinePlusSquare onClick={handlePlus} className="text-red-600" />
    </>
  );
}

import React from 'react';

export default function ProductDetailInfo({ detailInfo, name }) {
  return (
    <div>
      <h2 className="text-lg text-slate-600 mb-2">제품 Info</h2>
      <img className="w-full" src={detailInfo} alt={`${name}의 상세 이미지`} />
    </div>
  );
}

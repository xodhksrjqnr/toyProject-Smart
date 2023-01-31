import React from 'react';
import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductOrderForm from '../components/ProductOrderForm';
import ProductDetailImage from '../components/ProductDetailImage';
import ProductDetailInfo from '../components/ProductDetailInfo';

export default function ProductDetail() {
  const { id } = useParams();
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: productDetail,
  } = useQuery(['productDetail', id], () => product.getProduct(id), {
    staleTime: 1000 * 60 * 5,
  });
  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;
  if (productDetail.status === 404)
    return <p className="w-full">{productDetail.data.message}</p>;

  const { imgFiles, detailInfo, name } = productDetail;

  return (
    <div className="w-full p-8">
      <div className="flex mb-8 justify-between">
        <ProductDetailImage imgFiles={imgFiles} name={name} />
        <ProductOrderForm productDetail={productDetail} />
      </div>
      <div className="h-4 w-full bg-slate-300 my-8"></div>
      <ProductDetailInfo detailInfo={detailInfo} name={name} />
    </div>
  );
}

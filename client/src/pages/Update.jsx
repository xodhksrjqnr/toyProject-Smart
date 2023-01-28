import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { update } from '../api/update';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import { categories } from '../constants/categories';

export default function Update() {
  const { id } = useParams();
  const [updated, setUpdated] = useState({});
  const [file, setFile] = useState([]);
  const [detail, setDetail] = useState();
  const [isUploading, setIsUploading] = useState(false);
  const client = useQueryClient();
  const navigate = useNavigate();
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: productInfo,
  } = useQuery(['productInfo', id], () => product.getProduct(id), {
    staleTime: 1000 * 60 * 5,
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'imgFiles') {
      for (const value of files) {
        setFile((prev) => [...prev, value]);
      }
      return;
    }
    if (name === 'detailInfo') {
      setDetail(files && files[0]);
      return;
    }
    setUpdated((updated) => ({ ...updated, [name]: value }));
  };

  useEffect(() => {
    if (!productInfo) return;
    const { id, name, price, code, size } = productInfo;
    setUpdated((updated) => ({ ...updated, id, name, price, code, size }));
  }, [productInfo]);

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsUploading(true);
    update(updated, file, detail, true) //
      .then((data) => {
        if (data !== 400) {
          navigate('/admin');
        }
      })
      .finally(() => {
        setIsUploading(false);
        client.invalidateQueries(['products']);
      });
  };

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;

  return (
    <section className="register-section w-full p-8 text-sm">
      <form
        className="flex flex-col lg:w-1/2"
        method="POST"
        encType="multipart/form-data"
        onSubmit={handleSubmit}
      >
        <label htmlFor="imgFiles">제품 이미지</label>
        <input
          id="imgFiles"
          type="file"
          accept="image/*"
          name="imgFiles"
          multiple="multiple"
          required
          onChange={handleChange}
        />
        <label htmlFor="detailInfo">제품 상세 이미지</label>
        <input
          id="detailInfo"
          type="file"
          accept="image/*"
          name="detailInfo"
          required
          onChange={handleChange}
        />
        <label htmlFor="name">제품 이름</label>
        <input
          id="name"
          type="text"
          name="name"
          value={updated.name ?? ''}
          placeholder="제품 이름"
          required
          onChange={handleChange}
        />
        <label htmlFor="price">제품 가격</label>
        <input
          id="price"
          type="number"
          name="price"
          min="0"
          value={updated.price ?? ''}
          placeholder="가격"
          required
          onChange={handleChange}
        />
        <label htmlFor="code">제품 분류</label>
        <select
          id="code"
          name="code"
          value={updated.code || ''}
          onChange={handleChange}
        >
          {categories.map((category) =>
            category.subdivision.map((item) => (
              <option key={item.code} value={item.code}>
                {item.title}
              </option>
            ))
          )}
        </select>
        <label htmlFor="size">제품 사이즈</label>
        <input
          id="size"
          type="text"
          name="size"
          value={updated.size ?? ''}
          placeholder="사이즈(콤마(,)로 구분)"
          required
          onChange={handleChange}
        />
        <button
          className="bg-rose-400 w-28 p-1 text-white rounded-lg"
          disabled={isUploading}
        >
          {isUploading ? '업로드중..' : '제품 등록하기'}
        </button>
      </form>
    </section>
  );
}

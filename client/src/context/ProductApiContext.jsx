import { createContext, useContext } from 'react';
import FakeProduct from '../api/fakeProduct';
import Product from '../api/product';

export const ProductApiContext = createContext();

// const product = new FakeProduct();
const product = new Product();

export function ProductApiProvider({ children }) {
  return (
    <ProductApiContext.Provider value={{ product }}>
      {children}
    </ProductApiContext.Provider>
  );
}

export function useProductApi() {
  return useContext(ProductApiContext);
}

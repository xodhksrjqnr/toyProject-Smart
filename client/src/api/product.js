import axios from 'axios';

export default class Product {
  constructor() {
    this.httpClient = axios.create({
      baseURL: process.env.REACT_APP_PRODUCT_API_URL,
    });
  }

  async popular() {
    return this.httpClient.get('products').then((res) => res.data);
  }
}

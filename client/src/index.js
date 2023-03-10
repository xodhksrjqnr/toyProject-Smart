import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/Home';
import NotFound from './pages/NotFound';
import CategoryProducts from './pages/CategoryProducts';
import ProductDetail from './pages/ProductDetail';
import Register from './pages/Register';
import Admin from './pages/Admin';
import Update from './pages/Update';
import Search from './pages/Search';
import Login from './pages/Login';
import Carts from './pages/Carts';
import { UserProvider } from './context/UserContext';
import ProtectedRoute from './pages/ProtectedRoute';
import Myorder from './pages/Myorder';
import SignUp from './pages/SignUp';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: '/', element: <Home /> },
      { path: '/categories/:id', element: <CategoryProducts /> },
      { path: '/products/:id', element: <ProductDetail /> },
      { path: '/search', element: <Search /> },
      { path: '/admin', element: <Admin /> },
      { path: '/admin/register', element: <Register /> },
      { path: '/admin/update/:id', element: <Update /> },
      { path: '/signup', element: <SignUp /> },
      {
        path: '/carts',
        element: (
          <ProtectedRoute>
            <Carts />
          </ProtectedRoute>
        ),
      },
      {
        path: '/myorder',
        element: (
          <ProtectedRoute>
            <Myorder />
          </ProtectedRoute>
        ),
      },
    ],
  },
  {
    path: '/login',
    element: <Login />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <UserProvider>
      <RouterProvider router={router} />
    </UserProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

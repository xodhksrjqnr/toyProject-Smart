import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import SideNav from './components/SideNav';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

function App() {
  return (
    <>
      <Header />
      <SideNav />
      <QueryClientProvider client={queryClient}>
        <Outlet />
      </QueryClientProvider>
    </>
  );
}

export default App;

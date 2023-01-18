import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import SideNav from './components/SideNav';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

function App() {
  return (
    <>
      <Header />
      <main className="flex justify-center max-w-screen-2xl w-full">
        <SideNav />
        <QueryClientProvider client={queryClient}>
          <Outlet />
        </QueryClientProvider>
      </main>
    </>
  );
}

export default App;

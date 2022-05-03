import react from "react";
import FetchWatchReports from "./FetchWatchReports";
import { QueryClient, QueryClientProvider, useQuery } from "react-query";

const queryClient = new QueryClient();

const fetchWatchReports = async () => {
  const res = await fetch("https://jsonplaceholder.typicode.com/users");
  return res.json();
};

const WatchList = () => {
  const { data, status } = useQuery("watch", fetchWatchReports);
  console.log("data ", data, "status", status);
  return (
    <div>
      <h2>Watch List</h2>
      {status === "loading" && <div>Loading. Please Wait</div>}
      {status === "error" && <div>Error fetching data</div>}
      {status === "success" && (
        <div>
          {data.map((item) => (<FetchWatchReports key={item.id} item={item} />
          ))}
        </div>
      )}
    </div>
  );
};

export default function Wrapped() {
  return (
    <QueryClientProvider client={queryClient}>
      <WatchList />
    </QueryClientProvider>
  );
}


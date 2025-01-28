import apiClient from "@app/api/client";

export async function sayHello() {
  const { data } = await apiClient.get("/");

  console.debug("Data: ", data);

  return data;
}

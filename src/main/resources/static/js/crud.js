const baseUrl = "http://localhost:8080/api";
const headersList = {
  "Content-Type": "application/json",
  Accept: "application/json",
};
const btnAdd = document.getElementById("btnAdd");
const formContainer = document.getElementById("formContainer");
const actionTitle = document.getElementById("actionTitle");
const cardForm = document.getElementById("cardForm");
const stForm = document.getElementById("stForm");
const btnSend = document.getElementById("btnSend");
const btnClear = document.getElementById("btnClear");

const btnOrigin = document.getElementById("btnOrigin");
const btnDestination = document.getElementById("btnDestination");
const btnLayover = document.getElementById("btnLayover");
const filterAction = document.getElementById("filterAction");
const filterInput = document.getElementById("filterInput");
const filterLabel = document.getElementById("filterLabel");
const filterContainer = document.getElementById("filterContainer");
const btnReset = document.getElementById("btnReset");
const btnDeleteFilter = document.getElementById("btnDeleteFilter");
const btnDelCont = document.getElementById("btnDelCont");

let edit;
getFlights = async () => {
  let response = await fetch(`${baseUrl}/flights`, {
    method: "GET",
    headers: headersList,
  });
  if (!response.ok) throw new Error(response.status);
  let data = await response.text();
  return data;
};

postflights = async (flights) => {
  let response = await fetch(`${baseUrl}/flights`, {
    method: "POST",
    headers: headersList,
    body: JSON.stringify(flights),
  });
  if (!response.ok) throw new Error(response.status);

  let data = await response.text();
  return data;
};

putflights = async (flights) => {
  let response = await fetch(`${baseUrl}/flights/${flights.flightId}`, {
    method: "PUT",
    headers: headersList,
    body: JSON.stringify(flights),
  });
  if (!response.ok) throw new Error(response.status);
  let data = await response.text();
  return data;
};

deleteflights = async (id) => {
  let response = await fetch(`${baseUrl}/flights/${id}`, {
    method: "DELETE",
    headers: headersList,
  });
  if (!response.ok) {
    throw new Error(response.status);
    Toastify({
      text: "Ha ocurrido un error",
      duration: 2000,
      style: {
        background: "linear-gradient(to right, #7e202a, #b22d3b)",
      },
    }).showToast();
  }
  let data = await response.text();

  Toastify({
    text: "Eliminado correctamente",
    duration: 1000,
    style: {
      background: "linear-gradient(to right, #ffa070, #ff7d6e)",
    },
  }).showToast();
  setTimeout(() => {
    location.reload();
  }, 1000);
  return data;
};

getByOringin = async (origin) => {
  let response = await fetch(`${baseUrl}/flights/origin/${origin}`, {
    method: "GET",
    headers: headersList,
  });
  if (!response.ok) throw new Error(response.status);
  let data = await response.text();
  return data;
};

getByDestination = async (destination) => {
  let response = await fetch(`${baseUrl}/flights/destination/${destination}`, {
    method: "GET",
    headers: headersList,
  });
  if (!response.ok) throw new Error(response.status);
  let data = await response.text();
  return data;
};

getByLayover = async (layover) => {
  let response = await fetch(`${baseUrl}/flights/layoverCount/${layover}`, {
    method: "GET",
    headers: headersList,
  });
  if (!response.ok) throw new Error(response.status);
  let data = await response.text();
  return data;
};

deleteByDestination = async (destination) => {
  let response = await fetch(`${baseUrl}/flights/destination/${destination}`, {
    method: "DELETE",
    headers: headersList,
  });
  if (!response.ok) {
    throw new Error(response.status);
    Toastify({
      text: "Ha ocurrido un error",
      duration: 2000,
      style: {
        background: "linear-gradient(to right, #7e202a, #b22d3b)",
      },
    }).showToast();
  }
  let data = await response.text();

  Toastify({
    text: "Eliminado correctamente",
    duration: 1000,
    style: {
      background: "linear-gradient(to right, #ffa070, #ff7d6e)",
    },
  }).showToast();
  setTimeout(() => {
    location.reload();
  }, 1000);
  return data;
};


function showFlights() {
  getFlights()
    .then((data) => {
      let flights = JSON.parse(data);
      let table = document.getElementById("stTable");
      table.innerHTML = "";
      flights.forEach((flight) => {
        table.innerHTML += `
            <tr>
                <td style="text-align: center;">${flight.flightId}</td>
                <td>${flight.origin}</td>
                <td>${flight.destination}</td>
                <td>${flight.layoverCount}</td>
                <td>${flight.price}</td>
                <td>${flight.airline}</td>
                <td >
                    <div class="grid col-2">
                    <button class="btn btn-primary" onclick="editflights('${encodeURIComponent(
                      JSON.stringify(flight)
                    )}')"><i class='bx bx-message-square-edit'></i></button>
                    <button class="btn red" onclick="deleteflights(${
                      flight.flightId
                    })"><i class='bx bxs-trash'></i></button>
                    </div>
                </td>
            </tr>
            `;
      });
    })
    .catch((error) => console.log(error));
}

showFlights();

const readflights = (id) => {
  const origin = document.getElementById("origin").value;
  const destination = document.getElementById("destination").value;
  const layover = document.getElementById("layover").value;
  const price = document.getElementById("price").value;
  const airline = document.getElementById("airline").value;
  if (!id) {
    flightsObj = {
      origin: origin,
      destination: destination,
      layoverCount: layover,
      price: price,
      airline: airline,
    };
    return flightsObj;
  }
  flightsObj = {
    flightId: id,
    origin: origin,
    destination: destination,
    layoverCount: layover,
    price: price,
    airline: airline,
  };
  return flightsObj;
};

btnClear.addEventListener("click", (e) => {
  e.preventDefault();
  cardForm.classList.add("br-yellow");
  setTimeout(() => {
    cardForm.classList.remove("br-yellow");
  }, 1000);
  document.getElementById("origin").value = "";
  document.getElementById("destination").value = "";
  document.getElementById("layover").value = "";
  document.getElementById("price").value = "";
  document.getElementById("airline").value = "";
});

btnAdd.addEventListener("click", (e) => {
  formContainer.classList.remove("none");
  actionTitle.innerHTML = "Añadir Vuelo";
  cardForm.classList.add("br-green");
  btnSend.innerHTML = "Añadir";
  edit = false;
});

let selectID;
const editflights = (flightsData) => {
  const flights = JSON.parse(decodeURIComponent(flightsData));
  formContainer.classList.remove("none");
  actionTitle.innerHTML = "Editar Vuelo";
  cardForm.classList.add("br-blue");
  btnSend.innerHTML = "Editar";
  edit = true;
  document.getElementById("origin").value = flights.origin;
  document.getElementById("destination").value = flights.destination;
  document.getElementById("layover").value = flights.layoverCount;
  document.getElementById("price").value = flights.price;
  document.getElementById("airline").value = flights.airline;
  selectID = flights.flightId;
};

stForm.addEventListener("submit", (e) => {
  e.preventDefault();
  if (edit) {
    putflights(readflights(selectID))
      .then((data) => {
        showFlights();
        stForm.reset();
        formContainer.classList.add("none");
        cardForm.classList.remove("br-blue");
        Toastify({
          text: "Actualizado correctamente",
          duration: 2000,
          style: {
            background: "linear-gradient(to right, #00b09b, #6dc3c5)",
          },
        }).showToast();
      })
      .catch((error) => {
        console.log(error);
        Toastify({
          text: "Ha ocurrido un error",
          duration: 2000,
          style: {
            background: "linear-gradient(to right, #7e202a, #b22d3b)",
          },
        }).showToast();
      });
    return;
  }
  postflights(readflights())
    .then((data) => {
      showFlights();
      stForm.reset();
      formContainer.classList.add("none");
      cardForm.classList.remove("br-green");
      Toastify({
        text: "Añadido correctamente",
        duration: 2000,
        style: {
          background: "linear-gradient(to right, #00b09b, #96c93d)",
        },
      }).showToast();
    })
    .catch((error) => {
      console.log(error);
      Toastify({
        text: "Ha ocurrido un error",
        duration: 2000,
        style: {
          background: "linear-gradient(to right, #7e202a, #b22d3b)",
        },
      }).showToast();
    });
});

let typeOfFilter = "";

btnOrigin.addEventListener("click", (e) => {
  filterAction.innerHTML = "Buscar";
  filterInput.setAttribute("type", "text");
  filterLabel.innerHTML = "Origen";
  filterContainer.classList.remove("none");
  btnDelCont.classList.add("none");
  typeOfFilter = "origin";
});

btnDestination.addEventListener("click", (e) => {
  filterAction.innerHTML = "Buscar";
  filterInput.setAttribute("type", "text");
  filterLabel.innerHTML = "Destino";
  filterContainer.classList.remove("none");
  filterInput.addEventListener("keyup", (e) => {
    btnDelCont.classList.remove("none");
   });
  typeOfFilter = "destination";
  
});

btnLayover.addEventListener("click", (e) => {
  filterAction.innerHTML = "Buscar";
  btnDelCont.classList.add("none");
  filterContainer.classList.remove("none");
  filterInput.setAttribute("type", "number");
  filterLabel.innerHTML = "Escalas";
  typeOfFilter = "layover";
  
});

btnDeleteFilter.addEventListener("click", (e) => {
  deleteByDestination(filterInput.value)
  
});

filterAction.addEventListener("click", async (e) => {
  const value = filterInput.value;
  
  let flights = [];

  if (typeOfFilter === "origin") {
    flights = await getByOringin(value);
  } else if (typeOfFilter === "destination") {
    flights = await getByDestination(value);
  } else if (typeOfFilter === "layover") {
    flights = await getByLayover(value);
  }

  displayFlights(flights);
});

function displayFlights(data) {
  let flights = JSON.parse(data);
  let table = document.getElementById("stTable");
  table.innerHTML = "";
  flights.forEach((flight) => {
    table.innerHTML += `
        <tr>
            <td style="text-align: center;">${flight.flightId}</td>
            <td>${flight.origin}</td>
            <td>${flight.destination}</td>
            <td>${flight.layoverCount}</td>
            <td>${flight.price}</td>
            <td>${flight.airline}</td>
            <td >
                <div class="grid col-2">
                <button class="btn btn-primary" onclick="editflights('${encodeURIComponent(
                  JSON.stringify(flight)
                )}')"><i class='bx bx-message-square-edit'></i></button>
                <button class="btn red" onclick="deleteflights(${
                  flight.flightId
                })"><i class='bx bxs-trash'></i></button>
                </div>
            </td>
        </tr>
        `;
  });
}



btnReset.addEventListener("click", () => {
  filterInput.value = "";
  filterContainer.classList.add("none");
  showFlights();
});

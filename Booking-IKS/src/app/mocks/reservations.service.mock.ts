import {RequestStatus, ReservationRequest} from "../accommodations/accommodation/model/model.module";

const mockRequest1: ReservationRequest = {
  id: 1,
  accommodation:undefined,
  status:RequestStatus.PENDING,
  guest:undefined,
  guestNumber:2,
  price:200,
  timeSlot:undefined
};


export { mockRequest1 };

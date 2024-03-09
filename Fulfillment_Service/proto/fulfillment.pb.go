// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.28.1
// 	protoc        v4.25.3
// source: proto/fulfillment.proto

package fulfillment

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type AssignedOrder_Status int32

const (
	AssignedOrder_ASSIGNED   AssignedOrder_Status = 0
	AssignedOrder_IN_TRANSIT AssignedOrder_Status = 1
	AssignedOrder_DELIVERED  AssignedOrder_Status = 2
)

// Enum value maps for AssignedOrder_Status.
var (
	AssignedOrder_Status_name = map[int32]string{
		0: "ASSIGNED",
		1: "IN_TRANSIT",
		2: "DELIVERED",
	}
	AssignedOrder_Status_value = map[string]int32{
		"ASSIGNED":   0,
		"IN_TRANSIT": 1,
		"DELIVERED":  2,
	}
)

func (x AssignedOrder_Status) Enum() *AssignedOrder_Status {
	p := new(AssignedOrder_Status)
	*p = x
	return p
}

func (x AssignedOrder_Status) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (AssignedOrder_Status) Descriptor() protoreflect.EnumDescriptor {
	return file_proto_fulfillment_proto_enumTypes[0].Descriptor()
}

func (AssignedOrder_Status) Type() protoreflect.EnumType {
	return &file_proto_fulfillment_proto_enumTypes[0]
}

func (x AssignedOrder_Status) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Use AssignedOrder_Status.Descriptor instead.
func (AssignedOrder_Status) EnumDescriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{2, 0}
}

type Location struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Latitude  float64 `protobuf:"fixed64,1,opt,name=latitude,proto3" json:"latitude,omitempty"`
	Longitude float64 `protobuf:"fixed64,2,opt,name=longitude,proto3" json:"longitude,omitempty"`
}

func (x *Location) Reset() {
	*x = Location{}
	if protoimpl.UnsafeEnabled {
		mi := &file_proto_fulfillment_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Location) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Location) ProtoMessage() {}

func (x *Location) ProtoReflect() protoreflect.Message {
	mi := &file_proto_fulfillment_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Location.ProtoReflect.Descriptor instead.
func (*Location) Descriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{0}
}

func (x *Location) GetLatitude() float64 {
	if x != nil {
		return x.Latitude
	}
	return 0
}

func (x *Location) GetLongitude() float64 {
	if x != nil {
		return x.Longitude
	}
	return 0
}

type DeliveryPersonnel struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id           int64     `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
	Name         string    `protobuf:"bytes,2,opt,name=name,proto3" json:"name,omitempty"`
	Location     *Location `protobuf:"bytes,3,opt,name=location,proto3" json:"location,omitempty"`
	Availability bool      `protobuf:"varint,4,opt,name=availability,proto3" json:"availability,omitempty"`
}

func (x *DeliveryPersonnel) Reset() {
	*x = DeliveryPersonnel{}
	if protoimpl.UnsafeEnabled {
		mi := &file_proto_fulfillment_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *DeliveryPersonnel) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*DeliveryPersonnel) ProtoMessage() {}

func (x *DeliveryPersonnel) ProtoReflect() protoreflect.Message {
	mi := &file_proto_fulfillment_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use DeliveryPersonnel.ProtoReflect.Descriptor instead.
func (*DeliveryPersonnel) Descriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{1}
}

func (x *DeliveryPersonnel) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

func (x *DeliveryPersonnel) GetName() string {
	if x != nil {
		return x.Name
	}
	return ""
}

func (x *DeliveryPersonnel) GetLocation() *Location {
	if x != nil {
		return x.Location
	}
	return nil
}

func (x *DeliveryPersonnel) GetAvailability() bool {
	if x != nil {
		return x.Availability
	}
	return false
}

type AssignedOrder struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id                  int64                `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
	OrderId             int64                `protobuf:"varint,2,opt,name=order_id,json=orderId,proto3" json:"order_id,omitempty"`
	DeliveryPersonnelId int64                `protobuf:"varint,3,opt,name=delivery_personnel_id,json=deliveryPersonnelId,proto3" json:"delivery_personnel_id,omitempty"`
	Status              AssignedOrder_Status `protobuf:"varint,4,opt,name=status,proto3,enum=fulfillment.AssignedOrder_Status" json:"status,omitempty"`
}

func (x *AssignedOrder) Reset() {
	*x = AssignedOrder{}
	if protoimpl.UnsafeEnabled {
		mi := &file_proto_fulfillment_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *AssignedOrder) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*AssignedOrder) ProtoMessage() {}

func (x *AssignedOrder) ProtoReflect() protoreflect.Message {
	mi := &file_proto_fulfillment_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use AssignedOrder.ProtoReflect.Descriptor instead.
func (*AssignedOrder) Descriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{2}
}

func (x *AssignedOrder) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

func (x *AssignedOrder) GetOrderId() int64 {
	if x != nil {
		return x.OrderId
	}
	return 0
}

func (x *AssignedOrder) GetDeliveryPersonnelId() int64 {
	if x != nil {
		return x.DeliveryPersonnelId
	}
	return 0
}

func (x *AssignedOrder) GetStatus() AssignedOrder_Status {
	if x != nil {
		return x.Status
	}
	return AssignedOrder_ASSIGNED
}

type GetDeliveryPersonnelByIDRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id int64 `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
}

func (x *GetDeliveryPersonnelByIDRequest) Reset() {
	*x = GetDeliveryPersonnelByIDRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_proto_fulfillment_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetDeliveryPersonnelByIDRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetDeliveryPersonnelByIDRequest) ProtoMessage() {}

func (x *GetDeliveryPersonnelByIDRequest) ProtoReflect() protoreflect.Message {
	mi := &file_proto_fulfillment_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetDeliveryPersonnelByIDRequest.ProtoReflect.Descriptor instead.
func (*GetDeliveryPersonnelByIDRequest) Descriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{3}
}

func (x *GetDeliveryPersonnelByIDRequest) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

type AssignOrderRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	OrderId            int64     `protobuf:"varint,1,opt,name=order_id,json=orderId,proto3" json:"order_id,omitempty"`
	RestaurantLocation *Location `protobuf:"bytes,2,opt,name=restaurant_location,json=restaurantLocation,proto3" json:"restaurant_location,omitempty"`
	CustomerLocation   *Location `protobuf:"bytes,3,opt,name=customer_location,json=customerLocation,proto3" json:"customer_location,omitempty"`
}

func (x *AssignOrderRequest) Reset() {
	*x = AssignOrderRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_proto_fulfillment_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *AssignOrderRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*AssignOrderRequest) ProtoMessage() {}

func (x *AssignOrderRequest) ProtoReflect() protoreflect.Message {
	mi := &file_proto_fulfillment_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use AssignOrderRequest.ProtoReflect.Descriptor instead.
func (*AssignOrderRequest) Descriptor() ([]byte, []int) {
	return file_proto_fulfillment_proto_rawDescGZIP(), []int{4}
}

func (x *AssignOrderRequest) GetOrderId() int64 {
	if x != nil {
		return x.OrderId
	}
	return 0
}

func (x *AssignOrderRequest) GetRestaurantLocation() *Location {
	if x != nil {
		return x.RestaurantLocation
	}
	return nil
}

func (x *AssignOrderRequest) GetCustomerLocation() *Location {
	if x != nil {
		return x.CustomerLocation
	}
	return nil
}

var File_proto_fulfillment_proto protoreflect.FileDescriptor

var file_proto_fulfillment_proto_rawDesc = []byte{
	0x0a, 0x17, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x2f, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d,
	0x65, 0x6e, 0x74, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x0b, 0x66, 0x75, 0x6c, 0x66, 0x69,
	0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x22, 0x44, 0x0a, 0x08, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69,
	0x6f, 0x6e, 0x12, 0x1a, 0x0a, 0x08, 0x6c, 0x61, 0x74, 0x69, 0x74, 0x75, 0x64, 0x65, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x01, 0x52, 0x08, 0x6c, 0x61, 0x74, 0x69, 0x74, 0x75, 0x64, 0x65, 0x12, 0x1c,
	0x0a, 0x09, 0x6c, 0x6f, 0x6e, 0x67, 0x69, 0x74, 0x75, 0x64, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x01, 0x52, 0x09, 0x6c, 0x6f, 0x6e, 0x67, 0x69, 0x74, 0x75, 0x64, 0x65, 0x22, 0x8e, 0x01, 0x0a,
	0x11, 0x44, 0x65, 0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x50, 0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e,
	0x65, 0x6c, 0x12, 0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52, 0x02,
	0x69, 0x64, 0x12, 0x12, 0x0a, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09,
	0x52, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x12, 0x31, 0x0a, 0x08, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69,
	0x6f, 0x6e, 0x18, 0x03, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x15, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69,
	0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x2e, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52,
	0x08, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x12, 0x22, 0x0a, 0x0c, 0x61, 0x76, 0x61,
	0x69, 0x6c, 0x61, 0x62, 0x69, 0x6c, 0x69, 0x74, 0x79, 0x18, 0x04, 0x20, 0x01, 0x28, 0x08, 0x52,
	0x0c, 0x61, 0x76, 0x61, 0x69, 0x6c, 0x61, 0x62, 0x69, 0x6c, 0x69, 0x74, 0x79, 0x22, 0xe0, 0x01,
	0x0a, 0x0d, 0x41, 0x73, 0x73, 0x69, 0x67, 0x6e, 0x65, 0x64, 0x4f, 0x72, 0x64, 0x65, 0x72, 0x12,
	0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52, 0x02, 0x69, 0x64, 0x12,
	0x19, 0x0a, 0x08, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x03, 0x52, 0x07, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x49, 0x64, 0x12, 0x32, 0x0a, 0x15, 0x64, 0x65,
	0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x5f, 0x70, 0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e, 0x65, 0x6c,
	0x5f, 0x69, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x03, 0x52, 0x13, 0x64, 0x65, 0x6c, 0x69, 0x76,
	0x65, 0x72, 0x79, 0x50, 0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e, 0x65, 0x6c, 0x49, 0x64, 0x12, 0x39,
	0x0a, 0x06, 0x73, 0x74, 0x61, 0x74, 0x75, 0x73, 0x18, 0x04, 0x20, 0x01, 0x28, 0x0e, 0x32, 0x21,
	0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x2e, 0x41, 0x73, 0x73,
	0x69, 0x67, 0x6e, 0x65, 0x64, 0x4f, 0x72, 0x64, 0x65, 0x72, 0x2e, 0x53, 0x74, 0x61, 0x74, 0x75,
	0x73, 0x52, 0x06, 0x73, 0x74, 0x61, 0x74, 0x75, 0x73, 0x22, 0x35, 0x0a, 0x06, 0x53, 0x74, 0x61,
	0x74, 0x75, 0x73, 0x12, 0x0c, 0x0a, 0x08, 0x41, 0x53, 0x53, 0x49, 0x47, 0x4e, 0x45, 0x44, 0x10,
	0x00, 0x12, 0x0e, 0x0a, 0x0a, 0x49, 0x4e, 0x5f, 0x54, 0x52, 0x41, 0x4e, 0x53, 0x49, 0x54, 0x10,
	0x01, 0x12, 0x0d, 0x0a, 0x09, 0x44, 0x45, 0x4c, 0x49, 0x56, 0x45, 0x52, 0x45, 0x44, 0x10, 0x02,
	0x22, 0x31, 0x0a, 0x1f, 0x47, 0x65, 0x74, 0x44, 0x65, 0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x50,
	0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e, 0x65, 0x6c, 0x42, 0x79, 0x49, 0x44, 0x52, 0x65, 0x71, 0x75,
	0x65, 0x73, 0x74, 0x12, 0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52,
	0x02, 0x69, 0x64, 0x22, 0xbb, 0x01, 0x0a, 0x12, 0x41, 0x73, 0x73, 0x69, 0x67, 0x6e, 0x4f, 0x72,
	0x64, 0x65, 0x72, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x19, 0x0a, 0x08, 0x6f, 0x72,
	0x64, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52, 0x07, 0x6f, 0x72,
	0x64, 0x65, 0x72, 0x49, 0x64, 0x12, 0x46, 0x0a, 0x13, 0x72, 0x65, 0x73, 0x74, 0x61, 0x75, 0x72,
	0x61, 0x6e, 0x74, 0x5f, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x18, 0x02, 0x20, 0x01,
	0x28, 0x0b, 0x32, 0x15, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74,
	0x2e, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52, 0x12, 0x72, 0x65, 0x73, 0x74, 0x61,
	0x75, 0x72, 0x61, 0x6e, 0x74, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x12, 0x42, 0x0a,
	0x11, 0x63, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72, 0x5f, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69,
	0x6f, 0x6e, 0x18, 0x03, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x15, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69,
	0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x2e, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52,
	0x10, 0x63, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72, 0x4c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f,
	0x6e, 0x32, 0xca, 0x01, 0x0a, 0x12, 0x46, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e,
	0x74, 0x53, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x12, 0x68, 0x0a, 0x18, 0x47, 0x65, 0x74, 0x44,
	0x65, 0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x50, 0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e, 0x65, 0x6c,
	0x42, 0x79, 0x49, 0x44, 0x12, 0x2c, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65,
	0x6e, 0x74, 0x2e, 0x47, 0x65, 0x74, 0x44, 0x65, 0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x50, 0x65,
	0x72, 0x73, 0x6f, 0x6e, 0x6e, 0x65, 0x6c, 0x42, 0x79, 0x49, 0x44, 0x52, 0x65, 0x71, 0x75, 0x65,
	0x73, 0x74, 0x1a, 0x1e, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74,
	0x2e, 0x44, 0x65, 0x6c, 0x69, 0x76, 0x65, 0x72, 0x79, 0x50, 0x65, 0x72, 0x73, 0x6f, 0x6e, 0x6e,
	0x65, 0x6c, 0x12, 0x4a, 0x0a, 0x0b, 0x41, 0x73, 0x73, 0x69, 0x67, 0x6e, 0x4f, 0x72, 0x64, 0x65,
	0x72, 0x12, 0x1f, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x2e,
	0x41, 0x73, 0x73, 0x69, 0x67, 0x6e, 0x4f, 0x72, 0x64, 0x65, 0x72, 0x52, 0x65, 0x71, 0x75, 0x65,
	0x73, 0x74, 0x1a, 0x1a, 0x2e, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74,
	0x2e, 0x41, 0x73, 0x73, 0x69, 0x67, 0x6e, 0x65, 0x64, 0x4f, 0x72, 0x64, 0x65, 0x72, 0x42, 0x0e,
	0x5a, 0x0c, 0x2f, 0x66, 0x75, 0x6c, 0x66, 0x69, 0x6c, 0x6c, 0x6d, 0x65, 0x6e, 0x74, 0x62, 0x06,
	0x70, 0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_proto_fulfillment_proto_rawDescOnce sync.Once
	file_proto_fulfillment_proto_rawDescData = file_proto_fulfillment_proto_rawDesc
)

func file_proto_fulfillment_proto_rawDescGZIP() []byte {
	file_proto_fulfillment_proto_rawDescOnce.Do(func() {
		file_proto_fulfillment_proto_rawDescData = protoimpl.X.CompressGZIP(file_proto_fulfillment_proto_rawDescData)
	})
	return file_proto_fulfillment_proto_rawDescData
}

var file_proto_fulfillment_proto_enumTypes = make([]protoimpl.EnumInfo, 1)
var file_proto_fulfillment_proto_msgTypes = make([]protoimpl.MessageInfo, 5)
var file_proto_fulfillment_proto_goTypes = []interface{}{
	(AssignedOrder_Status)(0),               // 0: fulfillment.AssignedOrder.Status
	(*Location)(nil),                        // 1: fulfillment.Location
	(*DeliveryPersonnel)(nil),               // 2: fulfillment.DeliveryPersonnel
	(*AssignedOrder)(nil),                   // 3: fulfillment.AssignedOrder
	(*GetDeliveryPersonnelByIDRequest)(nil), // 4: fulfillment.GetDeliveryPersonnelByIDRequest
	(*AssignOrderRequest)(nil),              // 5: fulfillment.AssignOrderRequest
}
var file_proto_fulfillment_proto_depIdxs = []int32{
	1, // 0: fulfillment.DeliveryPersonnel.location:type_name -> fulfillment.Location
	0, // 1: fulfillment.AssignedOrder.status:type_name -> fulfillment.AssignedOrder.Status
	1, // 2: fulfillment.AssignOrderRequest.restaurant_location:type_name -> fulfillment.Location
	1, // 3: fulfillment.AssignOrderRequest.customer_location:type_name -> fulfillment.Location
	4, // 4: fulfillment.FulfillmentService.GetDeliveryPersonnelByID:input_type -> fulfillment.GetDeliveryPersonnelByIDRequest
	5, // 5: fulfillment.FulfillmentService.AssignOrder:input_type -> fulfillment.AssignOrderRequest
	2, // 6: fulfillment.FulfillmentService.GetDeliveryPersonnelByID:output_type -> fulfillment.DeliveryPersonnel
	3, // 7: fulfillment.FulfillmentService.AssignOrder:output_type -> fulfillment.AssignedOrder
	6, // [6:8] is the sub-list for method output_type
	4, // [4:6] is the sub-list for method input_type
	4, // [4:4] is the sub-list for extension type_name
	4, // [4:4] is the sub-list for extension extendee
	0, // [0:4] is the sub-list for field type_name
}

func init() { file_proto_fulfillment_proto_init() }
func file_proto_fulfillment_proto_init() {
	if File_proto_fulfillment_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_proto_fulfillment_proto_msgTypes[0].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Location); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_proto_fulfillment_proto_msgTypes[1].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*DeliveryPersonnel); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_proto_fulfillment_proto_msgTypes[2].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*AssignedOrder); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_proto_fulfillment_proto_msgTypes[3].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetDeliveryPersonnelByIDRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_proto_fulfillment_proto_msgTypes[4].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*AssignOrderRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_proto_fulfillment_proto_rawDesc,
			NumEnums:      1,
			NumMessages:   5,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_proto_fulfillment_proto_goTypes,
		DependencyIndexes: file_proto_fulfillment_proto_depIdxs,
		EnumInfos:         file_proto_fulfillment_proto_enumTypes,
		MessageInfos:      file_proto_fulfillment_proto_msgTypes,
	}.Build()
	File_proto_fulfillment_proto = out.File
	file_proto_fulfillment_proto_rawDesc = nil
	file_proto_fulfillment_proto_goTypes = nil
	file_proto_fulfillment_proto_depIdxs = nil
}

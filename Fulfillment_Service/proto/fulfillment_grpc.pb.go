// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.2.0
// - protoc             v4.25.3
// source: proto/fulfillment.proto

package fulfillment

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

// FulfillmentServiceClient is the client API for FulfillmentService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type FulfillmentServiceClient interface {
	GetDeliveryPersonnelByID(ctx context.Context, in *GetDeliveryPersonnelByIDRequest, opts ...grpc.CallOption) (*DeliveryPersonnel, error)
	AssignOrder(ctx context.Context, in *AssignOrderRequest, opts ...grpc.CallOption) (*AssignedOrder, error)
}

type fulfillmentServiceClient struct {
	cc grpc.ClientConnInterface
}

func NewFulfillmentServiceClient(cc grpc.ClientConnInterface) FulfillmentServiceClient {
	return &fulfillmentServiceClient{cc}
}

func (c *fulfillmentServiceClient) GetDeliveryPersonnelByID(ctx context.Context, in *GetDeliveryPersonnelByIDRequest, opts ...grpc.CallOption) (*DeliveryPersonnel, error) {
	out := new(DeliveryPersonnel)
	err := c.cc.Invoke(ctx, "/fulfillment.FulfillmentService/GetDeliveryPersonnelByID", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *fulfillmentServiceClient) AssignOrder(ctx context.Context, in *AssignOrderRequest, opts ...grpc.CallOption) (*AssignedOrder, error) {
	out := new(AssignedOrder)
	err := c.cc.Invoke(ctx, "/fulfillment.FulfillmentService/AssignOrder", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// FulfillmentServiceServer is the server API for FulfillmentService service.
// All implementations must embed UnimplementedFulfillmentServiceServer
// for forward compatibility
type FulfillmentServiceServer interface {
	GetDeliveryPersonnelByID(context.Context, *GetDeliveryPersonnelByIDRequest) (*DeliveryPersonnel, error)
	AssignOrder(context.Context, *AssignOrderRequest) (*AssignedOrder, error)
	mustEmbedUnimplementedFulfillmentServiceServer()
}

// UnimplementedFulfillmentServiceServer must be embedded to have forward compatible implementations.
type UnimplementedFulfillmentServiceServer struct {
}

func (UnimplementedFulfillmentServiceServer) GetDeliveryPersonnelByID(context.Context, *GetDeliveryPersonnelByIDRequest) (*DeliveryPersonnel, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetDeliveryPersonnelByID not implemented")
}
func (UnimplementedFulfillmentServiceServer) AssignOrder(context.Context, *AssignOrderRequest) (*AssignedOrder, error) {
	return nil, status.Errorf(codes.Unimplemented, "method AssignOrder not implemented")
}
func (UnimplementedFulfillmentServiceServer) mustEmbedUnimplementedFulfillmentServiceServer() {}

// UnsafeFulfillmentServiceServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to FulfillmentServiceServer will
// result in compilation errors.
type UnsafeFulfillmentServiceServer interface {
	mustEmbedUnimplementedFulfillmentServiceServer()
}

func RegisterFulfillmentServiceServer(s grpc.ServiceRegistrar, srv FulfillmentServiceServer) {
	s.RegisterService(&FulfillmentService_ServiceDesc, srv)
}

func _FulfillmentService_GetDeliveryPersonnelByID_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(GetDeliveryPersonnelByIDRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(FulfillmentServiceServer).GetDeliveryPersonnelByID(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/fulfillment.FulfillmentService/GetDeliveryPersonnelByID",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(FulfillmentServiceServer).GetDeliveryPersonnelByID(ctx, req.(*GetDeliveryPersonnelByIDRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _FulfillmentService_AssignOrder_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(AssignOrderRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(FulfillmentServiceServer).AssignOrder(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/fulfillment.FulfillmentService/AssignOrder",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(FulfillmentServiceServer).AssignOrder(ctx, req.(*AssignOrderRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// FulfillmentService_ServiceDesc is the grpc.ServiceDesc for FulfillmentService service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var FulfillmentService_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "fulfillment.FulfillmentService",
	HandlerType: (*FulfillmentServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "GetDeliveryPersonnelByID",
			Handler:    _FulfillmentService_GetDeliveryPersonnelByID_Handler,
		},
		{
			MethodName: "AssignOrder",
			Handler:    _FulfillmentService_AssignOrder_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "proto/fulfillment.proto",
}
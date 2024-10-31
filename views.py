from django.shortcuts import render,redirect
from django.http import HttpResponse
from django.http import JsonResponse
from django.views import View
from . models import Product,Cart,Customer,Wishlist
from django.db.models import Count
from . forms import CustomerRegistrationForm,CustomerProfileForm,Customer
from django.contrib import messages
from django.db.models import Q
from django.db import transaction
from django.conf import settings
# Create your views here.

from django.template.loader import get_template

def home(request):
    totalitem=0
    wishitem=0
    if request.user.is_authenticated:
        totalitem = len(Cart.objects.filter(user=request.user))
        wishitem=len(Wishlist.objects.filter(user=request.user))
    return render(request, 'home.html',locals())
def about(request):
    totalitem=0
    wishitem=0
    if request.user.is_authenticated:
        totalitem = len(Cart.objects.filter(user=request.user))
        wishitem=len(Wishlist.objects.filter(user=request.user))
    return render(request, 'about.html',locals())
def contact(request):
    totalitem=0
    wishitem=0
    if request.user.is_authenticated:
        totalitem = len(Cart.objects.filter(user=request.user))
        wishitem=len(Wishlist.objects.filter(user=request.user))
    return render(request, 'contact.html',locals())

class CategoryView(View):
    def get(self,request,val):
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        product = Product.objects.filter(category=val)
        title = Product.objects.filter(category=val).values('title')
        return render(request,"category.html",locals())
class CategoryTitle(View):
    def get(self,request,val):
        product = Product.objects.filter(title=val)
        title = Product.objects.filter(category=product[0].category).values('title')
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        return render(request,"category.html",locals())
    
class ProductDetail(View):
    def get(self,request,pk):
        product = Product.objects.get(pk=pk)
        wishlist = Wishlist.objects.filter(Q(product=product) & Q(user=request.user))
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        return render(request,"productdetail.html",locals())
    
class CustomerRegistrationView(View):
    def get(self,request):
        form=CustomerRegistrationForm()
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        return render(request, "customerregistration.html",locals())
    def post(self,request):
        form=CustomerRegistrationForm(request.POST)
        if form.is_valid():
            form.save()
            messages.success(request,"Congratulations! User Registration Successfully")
        else:
            messages.warning(request,"Invalid Input Data")
        return render(request, "customerregistration.html",locals())
    
class ProfileView(View):
    def get(self,request):
        form = CustomerProfileForm()
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        return render(request, "profile.html",locals())
              
    def post(self,request):
        form=CustomerProfileForm(request.POST)
        if form.is_valid():
            user = request.user
            name = form.cleaned_data['name']
            locality =form.cleaned_data['locality']
            city= form.cleaned_data['city']
            mobile =form.cleaned_data['mobile']
            state = form.cleaned_data['state']
            zipcode = form.cleaned_data['zipcode']
            reg = Customer(user=user, name=name, locality=locality, mobile=mobile, city=city, state=state, zipcode=zipcode)
            reg.save()
            messages.success(request,"Congratulations! Profile Save Successfully")
        else:
            messages.warning(request,"Invalid Input Data")
        return render(request, "profile.html",locals())
def address(request):
    add=Customer.objects.filter(user=request.user)
    totalitem=0
    wishitem=0
    if request.user.is_authenticated:
        totalitem = len(Cart.objects.filter(user=request.user))
        wishitem=len(Wishlist.objects.filter(user=request.user))
    return render(request, "address.html",locals())
class updateAddress(View):
    def get(self,request,pk):
        add = Customer.objects.get(pk=pk)
        form = CustomerProfileForm(instance=add)
        totalitem=0
        wishitem=0
        if request.user.is_authenticated:
            totalitem = len(Cart.objects.filter(user=request.user))
            wishitem=len(Wishlist.objects.filter(user=request.user))
        return render(request, "updateAddress.html",locals())
    def post(self,request,pk):
        form=CustomerProfileForm(request.POST)
        return render(request, "updateAddress.html",locals())
# def add_to_cart(request):
#     user=request.user
#     product_id=request.GET.get('prod_id')
#     product=product.objects.get(id=product_id)
#     Cart(user=user,product=product).save()
#     return redirect("/cart")

def add_to_cart(request):
    if request.user.is_authenticated:
        user = request.user
        product_id = request.GET.get('prod_id')
        product = Product.objects.get(id=product_id)
        Cart(user=user, product=product).save()
        return redirect("/cart")
    else:
        return redirect('login')  # Redirect to login if not authenticated

# def show_cart(request):
#     user=request.user
#     cart=Cart.objects.filter(user=user)
#     amount=0
#     for p in cart:
#         value=p.quantity*p.product.discounted_price
#         amount=amount+value
#     totalamount= amount+40
#     totalitem=0
#     wishitem=0
#     if request.user.is_authenticated:
#         totalitem = len(Cart.objects.filter(user=request.user))
#         wishitem=len(Wishlist.objects.filter(user=request.user))
#     return render(request,"addtocart.html",locals())

def show_cart(request):
    if request.user.is_authenticated:
        user = request.user
        cart = Cart.objects.filter(user=user)
        amount = 0
        for p in cart:
            value = p.quantity * p.product.discounted_price
            amount += value
        totalamount = amount + 40
        totalitem = len(Cart.objects.filter(user=request.user))
        wishitem = len(Wishlist.objects.filter(user=request.user))
        return render(request, "addtocart.html", locals())
    else:
        return redirect('login')


def plus_cart(request):
    if request.method == 'GET':
            prod_id = request.GET['prod_id']
            c = Cart.objects.get(Q(product=prod_id) & Q(user=request.user))
            with transaction.atomic():
                c.quantity += 1
                c.save()
                user = request.user
                cart = Cart.objects.filter(user=user)
                amount = 0
                for p in cart:
                    value = p.quantity * p.product.discounted_price
                    amount += value
                totalamount = amount + 40  
                data = {
                    'quantity': c.quantity,
                    'amount': amount,
                    'totalamount': totalamount
                }
                return JsonResponse(data)


def minus_cart(request):
    if request.method == 'GET':
            prod_id = request.GET['prod_id']
            c = Cart.objects.get(Q(product=prod_id) & Q(user=request.user))
            with transaction.atomic():
                c.quantity -= 1
                c.save()
                user = request.user
                cart = Cart.objects.filter(user=user)
                amount = 0
                for p in cart:
                    value = p.quantity * p.product.discounted_price
                    amount += value
                totalamount = amount + 40  
                data = {
                    'quantity': c.quantity,
                    'amount': amount,
                    'totalamount': totalamount
                }
                return JsonResponse(data)

def remove_cart(request):
    if request.method == 'GET':
            prod_id = request.GET['prod_id']
            c = Cart.objects.get(Q(product=prod_id) & Q(user=request.user))
            with transaction.atomic():
                c.delete()
                user = request.user
                cart = Cart.objects.filter(user=user)
                amount = 0
                for p in cart:
                    value = p.quantity * p.product.discounted_price
                    amount += value
                totalamount = amount + 40  
                data = {
                    'amount': amount,
                    'totalamount': totalamount
                }
                return JsonResponse(data)

def plus_wishlist(request):
    if request.method == 'GET':
        prod_id=request.GET['prod_id']
        product=Product.objects.get(id=prod_id)
        user=request.user
        Wishlist(user=user,product=product).save()
        data={
            'message':'Wishlist Added Successfully',
        }
        return JsonResponse(data)
                

def minus_wishlist(request):
    if request.method == 'GET':
        prod_id=request.GET['prod_id']
        product=Product.objects.get(id=prod_id)
        user=request.user
        Wishlist.objects.filter(user=user,product=product).delete()
        data={
            'message':'Wishlist Removed Successfully',
        }
        return JsonResponse(data)

                
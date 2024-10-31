from django.db import models
from django.contrib.auth.models import User
# Create your models here.
STATE_CHOICES=(('Andhra Pradesh','Andhra Pradesh'),('Telangana','Telangana'),)
CATEGORY_CHOICES=(
    ('FT', 'Fruits'),
    ('VG', 'Vegies'),
    ('DP', 'Dairy products'),
    ('SD', 'Seeds'),
    ('FZ', 'Fertilizers'),
    ('HN', 'Honey'),
    ('HB', 'Herbs'),
    ('FL', 'Flowers'),
)

class Product (models.Model):
    title = models.CharField(max_length=100)
    selling_price = models.FloatField()
    discounted_price = models.FloatField()
    description = models.TextField()
    composition = models.TextField()
    prodapp = models.TextField()
    category = models.CharField(choices=CATEGORY_CHOICES, max_length=2)
    product_image = models. ImageField(upload_to='product')
    def str(self):
        return self.title
class Customer(models.Model):
    user= models. ForeignKey (User, on_delete=models.CASCADE)
    name = models.CharField(max_length=200)
    locality= models. CharField(max_length=200)
    city= models. CharField(max_length=50)
    mobile =models.IntegerField()
    zipcode= models. IntegerField()
    state =models.CharField(choices=STATE_CHOICES, max_length=100)
    def __str__(self):
        return self.name
    
class Cart(models.Model):
    user=models.ForeignKey(User,on_delete=models.CASCADE)
    product=models.ForeignKey(Product,on_delete=models.CASCADE)
    quantity=models.PositiveIntegerField(default=1)

@property
def total_cost(self):
    return self.quantity*self.product.discounted_price

class Wishlist(models.Model):
    user=models.ForeignKey(User,on_delete=models.CASCADE)
    product=models.ForeignKey(Product,on_delete=models.CASCADE)
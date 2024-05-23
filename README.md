# microservice-template

az ad sp create-for-rbac \
    --role contributor \
    --scopes /subscriptions/f2ecdfbd-c9a8-424d-9fd6-b9662a147ca8 \
    --json-auth
    
az ad sp create-for-rbac \
    --role contributor \
    --scopes /subscriptions/f2ecdfbd-c9a8-424d-9fd6-b9662a147ca8/resourceGroups/rg-project-sb1 \
    --json-auth    
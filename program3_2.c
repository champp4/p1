#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void encryptMessage(char *message, char *cipher, int rails)
{
    int i, j, len, count, code[100][1000]; 
    len = strlen(message);
    for (i = 0; i < rails; i++)
    {
        for (j = 0; j < len; j++)
        {
            code[i][j] = 0; 
        }
    }
    count = 0;
    j = 0;
    while (j < len)
    {
        if (count % 2 == 0)
        {
            for (i = 0; i < rails; i++)
            {
                if (j < len)
                    code[i][j] = (int)message[j];
                j++;
            }
        }
        else
        {
            for (i = rails - 2; i > 0; i--)
            {
                if (j < len)
                    code[i][j] = (int)message[j];   
                j++;
            }
        }
        count++;
    }
    printf("\nRail Fence Pattern:\n");
    for (i = 0; i < rails; i++)
    {
        for (j = 0; j < len; j++)
        {
            if (code[i][j] != 0)
                printf("%c ", code[i][j]); 
            else
                printf("  ");
        }
        printf("\n");
    }
    int index = 0;
    for (i = 0; i < rails; i++)
    {
        for (j = 0; j < len; j++)
        {
            if (code[i][j] != 0)
            {
                cipher[index++] = (char)code[i][j];
            }
        }
    }
    cipher[index] = '\0';
}

void decryptMessage(char *message, char *decrpyted, int rails)
{
    int i, j, len, count, k, code[100][1000]; 
    len = strlen(message);

    for (i = 0; i < rails; i++)
    {
        for (j = 0; j < len; j++)
        {
            code[i][j] = 0; 
        }
    }
    count = 0;
    j = 0;
    while (j < len)
    {
        if (count % 2 == 0)
        {
            for (i = 0; i < rails; i++)
            {
                if (j < len)        
                    code[i][j] = 1; 
                j++;
            }
        }
        else
        {
            for (i = rails - 2; i > 0; i--)
            {
                if (j < len) 
                    code[i][j] = 1;
                j++;
            }
        }
        count++;
    }
    k = 0;
    for (i = 0; i < rails; i++)
    {
        for (j = 0; j < len; j++)
        {
            if (code[i][j] == 1)
            {
                code[i][j] = (int)message[k];
                k++;
            }
        }
    }
    count = 0;
    j = 0;
    int index = 0;
    while (j < len)
    {
        if (count % 2 == 0)
        {
            for (i = 0; i < rails; i++)
            {
                if (code[i][j] != 0)
                {
                    decrpyted[index++] = (char)code[i][j];
                    j++;
                }
            }
        }
        else
        {
            for (i = rails - 2; i > 0; i--)
            {
                if (code[i][j] != 0)
                {                        
                    decrpyted[index++] = (char)code[i][j];
                    j++;
                }
            }
        }
        count++;
    }
    decrpyted[index] = '\0';
}


int main()
{
    char message[1000], cipher[1000], decrpyted[1000];
    int rails, choice;

    printf("Enter a Secret Message : ");
    fgets(message, sizeof(message), stdin);
    message[strcspn(message, "\n")] = '\0';

    printf("Enter number of rails : ");
    scanf("%d", &rails);

    encryptMessage(message, cipher, rails);
    decryptMessage(cipher, decrpyted, rails);

    printf("\nEncrypted message : %s\n", cipher);
    printf("Decrypted message : %s\n", decrpyted);

    return 0;
}